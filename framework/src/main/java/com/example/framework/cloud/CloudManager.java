package com.example.framework.cloud;

import android.content.Context;

import com.example.framework.utils.LogUtils;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * FileName:CloudManager
 * Create Date:2020/1/27 18:00
 * Profile:
 */
public class CloudManager {
    //Url
    public static final String TOKEN_URL = "http://api-cn.ronghub.com/user/getToken.json";
    //Key
    public static final String CLOUD_KEY = "k51hidwqkvc5b";
    public static final String CLOUD_SECRET = "OHqaSFEZdDy";

    private static volatile CloudManager mInstance=null;
    private CloudManager(){

    }
    public static CloudManager getInstance(){
        if(mInstance==null){
            synchronized (CloudManager.class){
                if(mInstance==null){
                    mInstance=new CloudManager ();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化融云
     * @param mContext
     */
    public void initCloud(Context mContext){
        RongIMClient.init (mContext);
    }

    /**
     * 连接融云服务
     * @param token
     */
    public void connect(String token){
        RongIMClient.connect (token, new RongIMClient.ConnectCallback () {
            @Override
            public void onTokenIncorrect() {
                LogUtils.e ("Token Error");
            }

            @Override
            public void onSuccess(String s) {
                LogUtils.i ("Success:"+s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.e ("Error:"+errorCode);
            }
        });
    }


    /**
     * 断开连接
     */
    public void disconnect() {
        RongIMClient.getInstance().disconnect();
    }

    /**
     * 退出登录
     */
    public void logout() {
        RongIMClient.getInstance().logout();
    }

    /**
     * 接收消息的监听器
     *
     * @param listener
     */
    public void setOnReceiveMessageListener(RongIMClient.OnReceiveMessageListener listener) {
        RongIMClient.setOnReceiveMessageListener(listener);
    }


    /**
     * 发送消息的结果回调
     */
    private IRongCallback.ISendMessageCallback iSendMessageCallback=new IRongCallback.ISendMessageCallback () {
        @Override
        public void onAttached(Message message) {
            // 消息成功存到本地数据库的回调
        }

        @Override
        public void onSuccess(Message message) {
            // 消息发送成功的回调
            LogUtils.i("sendMessage onSuccess");
        }

        @Override
        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            // 消息发送失败的回调
            LogUtils.e("sendMessage onError:" + errorCode);
        }
    };

    /**
     * 发送文本消息
     * 一个手机 发送
     * 另外一个手机 接收
     *
     * @param msg
     * @param targetId
     */
    private void sendTextMessage(String msg,String targetId){
        LogUtils.i ("sendTextMessage");
        TextMessage textMessage=TextMessage.obtain (msg);
        RongIMClient.getInstance().sendMessage(
                Conversation.ConversationType.PRIVATE,
                targetId,
                textMessage,
                null,
                null,
                iSendMessageCallback
        );
    }

}