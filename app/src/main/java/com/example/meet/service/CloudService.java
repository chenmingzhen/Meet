package com.example.meet.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

import com.example.framework.cloud.CloudManager;
import com.example.framework.db.LitePalHelper;
import com.example.framework.entity.Constants;
import com.example.framework.gson.TextBean;
import com.example.framework.utils.LogUtils;
import com.example.framework.utils.SpUtils;
import com.google.gson.Gson;

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

            if (textBean.getType ().equals (CloudManager.TYPE_TEXT)) {

                //添加好友消息
            } else if (textBean.getType ().equals (CloudManager.TYPE_ADD_FRIEND)) {
                //存入数据库 Bmob RongCloud 都没有提供存储方法
                //使用另外的方法来实现 存入本地数据库
                LogUtils.i ("添加好友消息");
                LitePalHelper.getInstance ().saveNewFriend (textBean.getMsg (),message.getTargetId ());
                
            } else if (textBean.getType ().equals (CloudManager.TYPE_ARGEED_FRIEND)) {

            }
        }

    }

    @Override
    public void onClick(View v) {

    }
}
