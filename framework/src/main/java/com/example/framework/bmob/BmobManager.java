package com.example.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * FileName:BmobManager
 * Create Date:2020/1/14 18:28
 * Profile:
 */
public class BmobManager {

    private final static String BMOB_SDK_ID="b85cc2bd7b20f60bf7e94a68855b01c9";

    public volatile static BmobManager mInstance=null;

    private BmobManager(){

    }
    public static BmobManager getInstance(){
        if(mInstance==null){
            synchronized (BmobManager.class){
                if(mInstance==null){
                    mInstance=new BmobManager ();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化Bmob
     * @param context
     */
    public void initBmob(Context context){
        Bmob.initialize (context,BMOB_SDK_ID);
    }
}
