package com.example.framework.manager;

import com.example.framework.cloud.CloudManager;
import com.example.framework.utils.SHA1;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpManager {

    private static volatile HttpManager mInstance;
    private OkHttpClient mOkHttpClient;


    private HttpManager() {
        mOkHttpClient=new OkHttpClient ();
    }

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 请求融云Token
     * @param map
     * @return
     */
    public String postCloudToken(HashMap<String,String> map){

        //参数
        String Timestamp=String.valueOf (System.currentTimeMillis ()/1000);
        String Nonce=String.valueOf (Math.floor (Math.random ()*100000));
        String Signature= SHA1.sha1 (CloudManager.CLOUD_SECRET+Nonce+Timestamp);

        //参数填充
        FormBody.Builder builder=new FormBody.Builder ();
        for(String key:map.keySet ()){
            builder.add (key,map.get (key));
        }
        RequestBody requestBody=builder.build ();
        //添加签名规则
        Request request = new Request.Builder()
                .url(CloudManager.TOKEN_URL)
                .addHeader("Timestamp", Timestamp)
                .addHeader("App-Key", CloudManager.CLOUD_KEY)
                .addHeader("Nonce", Nonce)
                .addHeader("Signature", Signature)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        try {
            return mOkHttpClient.newCall (request).execute ().body ().string ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return "";
    }
}