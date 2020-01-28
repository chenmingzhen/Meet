package com.example.framework;

import android.content.Context;

import com.example.framework.bmob.BmobManager;
import com.example.framework.cloud.CloudManager;
import com.example.framework.utils.SpUtils;

public class Framework {
    private volatile static Framework mFramework;

    public Framework() {
    }

    public static Framework getFrameWork() {
        if (mFramework == null) {
            synchronized (Framework.class) {
                if (mFramework == null) {
                    mFramework = new Framework ();
                }
            }
        }
        return mFramework;
    }

    public void initFramework(Context context){
        //初始化SharedPreference
        SpUtils.getInstance ().initSp (context);
        BmobManager.getInstance ().initBmob (context);
        CloudManager.getInstance ().initCloud (context);
    }
}
