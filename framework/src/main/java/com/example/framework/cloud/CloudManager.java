package com.example.framework.cloud;

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
}
