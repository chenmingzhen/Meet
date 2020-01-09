package com.example.meet;

import android.app.Application;

import com.example.framework.utils.SpUtils;

/**
 * FileName:MyApplication
 * Create Date:2020/1/9 18:08
 * Profile:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate ();
        //初始化SharedPreference
        SpUtils.getInstance ().initSp (getApplicationContext ());
    }
}
