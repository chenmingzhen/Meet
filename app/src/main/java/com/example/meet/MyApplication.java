package com.example.meet;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.framework.Framework;


/**
 * FileName:MyApplication
 * Create Date:2020/1/9 18:08
 * Profile:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate ();


        /**
         * Application的优化
         * 1.必要的组件在程序主页去初始化
         * 2.如果组件一定要在App中初始化，那么尽可能的延时
         * 3.非必要的组件，子线程中初始化
         */
        //只在主进程中初始化
        if(getApplicationInfo ().packageName.equals (getCurProcessName (getApplicationContext ()))){
            Framework.getFrameWork ().initFramework (getApplicationContext ());
        }


    }

    /**
     * 获取当前进程名
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context){
      int pid=android.os.Process.myPid ();
        ActivityManager activityManager=(ActivityManager)context.getSystemService (Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo appProcess:activityManager.getRunningAppProcesses ()){
            if(appProcess.pid==pid){
                return appProcess.processName;
            }
        }
        return null;
    }
}
