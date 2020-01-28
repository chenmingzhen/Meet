package com.example.framework.cloud;

import android.content.Context;

import com.example.framework.utils.LogUtils;

import io.rong.imlib.RongIMClient;

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

}
