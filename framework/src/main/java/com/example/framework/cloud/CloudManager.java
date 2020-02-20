package com.example.framework.cloud;

import android.content.Context;

import com.example.framework.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;

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

    //ObjectName
    public static final String MSG_TEXT_NAME = "RC:TxtMsg";
    public static final String MSG_IMAGE_NAME = "RC:ImgMsg";
    public static final String MSG_LOCATION_NAME = "RC:LBSMsg";

    //普通消息
    public static final String TYPE_TEXT = "TYPE_TEXT";
    //添加好友消息
    public static final String TYPE_ADD_FRIEND = "TYPE_ADD_FRIEND";
    //同意添加好友的消息
    public static final String TYPE_ARGEED_FRIEND = "TYPE_AGREED_FRIEND";

    private static volatile CloudManager mInstance = null;

    private CloudManager() {

    }

    public static CloudManager getInstance() {
        if (mInstance == null) {
            synchronized (CloudManager.class) {
                if (mInstance == null) {
                    mInstance = new CloudManager ();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化融云
     *
     * @param mContext
     */
    public void initCloud(Context mContext) {
        RongIMClient.init (mContext);
    }

    /**
     * 连接融云服务
     *
     * @param token
     */
    public void connect(String token) {
        RongIMClient.connect (token, new RongIMClient.ConnectCallback () {
            @Override
            public void onTokenIncorrect() {
                LogUtils.e ("Token Error");
            }

            @Override
            public void onSuccess(String s) {
                LogUtils.i ("Success:" + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.e ("Error:" + errorCode);
            }
        });
    }


    /**
     * 断开连接
     */
    public void disconnect() {
        RongIMClient.getInstance ().disconnect ();
    }

    /**
     * 退出登录
     */
    public void logout() {
        RongIMClient.getInstance ().logout ();
    }

    /**
     * 接收消息的监听器
     *
     * @param listener
     */
    public void setOnReceiveMessageListener(RongIMClient.OnReceiveMessageListener listener) {
        RongIMClient.setOnReceiveMessageListener (listener);
    }


    /**
     * 发送消息的结果回调
     */
    private IRongCallback.ISendMessageCallback iSendMessageCallback = new IRongCallback.ISendMessageCallback () {
        @Override
        public void onAttached(Message message) {
            // 消息成功存到本地数据库的回调
        }

        @Override
        public void onSuccess(Message message) {
            // 消息发送成功的回调
            LogUtils.i ("sendMessage onSuccess");
        }

        @Override
        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            // 消息发送失败的回调
            LogUtils.e ("sendMessage onError:" + errorCode);
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
    private void sendTextMessage(String msg, String targetId) {
        LogUtils.i ("sendTextMessage");
        TextMessage textMessage = TextMessage.obtain (msg);
        RongIMClient.getInstance ().sendMessage (
                Conversation.ConversationType.PRIVATE,
                targetId,
                textMessage,
                null,
                null,
                iSendMessageCallback
        );
    }

    public void sendTextMessage(String msg, String type, String targetId) {
        JSONObject jsonObject = new JSONObject ();
        try {
            jsonObject.put ("msg", msg);
            //如果没有这个Type 就是一条普通消息
            jsonObject.put ("type", type);
            sendTextMessage (jsonObject.toString (), targetId);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    /**
     * 查询本地的会话记录
     *
     * @param callback
     */
    public void getConversationList(RongIMClient.ResultCallback<List<Conversation>> callback) {
        RongIMClient.getInstance ().getConversationList (callback);
    }

    /**
     * 加载本地的历史记录
     *
     * @param targetId
     * @param callback
     */
    public void getHistoryMessages(String targetId, RongIMClient.ResultCallback<List<Message>> callback) {
        RongIMClient.getInstance ().getHistoryMessages (Conversation.ConversationType.PRIVATE, targetId, -1, 1000, callback);
    }

    /**
     * 获取服务器的历史记录
     *
     * @param targetId
     * @param callback
     */
    public void getRemoteHistoryMessages(String targetId, RongIMClient.ResultCallback<List<Message>> callback) {
        RongIMClient.getInstance ().getRemoteHistoryMessages (Conversation.ConversationType.PRIVATE
                , targetId, 0, 20, callback);
    }


}
