package com.example.meet.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

import com.example.framework.bmob.BmobManager;
import com.example.framework.cloud.CloudManager;
import com.example.framework.db.LitePalHelper;
import com.example.framework.db.NewFriend;
import com.example.framework.entity.Constants;
import com.example.framework.event.EventManager;
import com.example.framework.event.MessageEvent;
import com.example.framework.gson.TextBean;
import com.example.framework.utils.CommonUtils;
import com.example.framework.utils.LogUtils;
import com.example.framework.utils.SpUtils;
import com.google.gson.Gson;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class CloudService extends Service implements View.OnClickListener {
    public CloudService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException ("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        linkCloudServer ();
    }

    /**
     * 连接云服务
     */
    public void linkCloudServer() {
        String token = SpUtils.getInstance ().getString (Constants.SP_TOKEN, "");
        CloudManager.getInstance ().connect (token);
        CloudManager.getInstance ().setOnReceiveMessageListener (new RongIMClient.OnReceiveMessageListener () {
            @Override
            public boolean onReceived(Message message, int i) {
                parsingImMessage (message);
                return false;
            }
        });
    }

    private void parsingImMessage(Message message) {
        LogUtils.i ("message:" + message);
        String objectName = message.getObjectName ();
        if (objectName.equals (CloudManager.MSG_TEXT_NAME)) {
            TextMessage textMessage = (TextMessage) message.getContent ();
            String content = textMessage.getContent ();
            LogUtils.i ("content:" + content);
            TextBean textBean = new Gson ().fromJson (content, TextBean.class);
            //普通消息
            if (textBean.getType ().equals (CloudManager.TYPE_TEXT)) {
                MessageEvent event=new MessageEvent ((EventManager.FLAG_SEND_TEXT));
                event.setText (textBean.getMsg ());
                event.setUserId (message.getSenderUserId ());
                EventManager.post (event);

                return;
                //添加好友消息
            } else if (textBean.getType ().equals (CloudManager.TYPE_ADD_FRIEND)) {
                //存入数据库 Bmob RongCloud 都没有提供存储方法
                //使用另外的方法来实现 存入本地数据库
                LogUtils.i ("添加好友消息");
                Disposable disposable=Observable.create (new ObservableOnSubscribe<List<NewFriend>> () {

                    @Override
                    public void subscribe(ObservableEmitter<List<NewFriend>> emitter) throws Exception {
                        emitter.onNext (LitePalHelper.getInstance ().queryNewFriend ());
                        emitter.onComplete ();
                    }
                }).subscribeOn (Schedulers.newThread ())
                        .observeOn (AndroidSchedulers.mainThread ())
                        .subscribe (new Consumer<List<NewFriend>> () {
                            @Override
                            public void accept(List<NewFriend> newFriends) throws Exception {
                                if (CommonUtils.isEmpty (newFriends)) {
                                    boolean isHave = false;
                                    for (int j = 0; j < newFriends.size (); j++) {
                                        NewFriend newFriend = newFriends.get (j);
                                        if (message.getSenderUserId ().equals (newFriend.getUserId ())) {
                                            isHave = true;
                                            break;
                                        }
                                    }
                                    //自己添加的 更新新的消息 替代旧的消息内容
                                    LitePalHelper.getInstance ().updateNewFriend (message.getTargetId (),textBean.getMsg ());
                                    //避免重复添加
                                    if (!isHave) {
                                        LitePalHelper.getInstance ().saveNewFriend (textBean.getMsg (), message.getTargetId ());
                                    }
                                } else {
                                    LitePalHelper.getInstance ().saveNewFriend (textBean.getMsg (), message.getTargetId ());
                                }
                            }
                        });

               //同意添加好友信息
            } else if (textBean.getType ().equals (CloudManager.TYPE_ARGEED_FRIEND)) {
               //1.添加好友到列表
                BmobManager.getInstance ().addFriend (message.getSenderUserId (), new SaveListener<String> () {
                    @Override
                    public void done(String s, BmobException e) {
                        //2.刷新好友列表
                        EventManager.post (EventManager.FLAG_UPDATE_FRIEND_LIST);
                    }
                });
            }
        }

    }

    @Override
    public void onClick(View v) {

    }
}
