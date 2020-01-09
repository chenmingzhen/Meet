package com.example.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.framework.BuildConfig;

/**
 * FileName:SpUtils
 * Create Date:2020/1/9 16:19
 * Profile:SharedPreferences帮助类
 */
public class SpUtils {
    private volatile static SpUtils mInstance = null;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * 单例模式获取SpUtils实例
     *
     * @return
     */
    public static SpUtils getInstance() {
        if (mInstance == null) {
            synchronized (SpUtils.class) {
                if (mInstance == null) {
                    mInstance = new SpUtils ();
                }
            }
        }
        return mInstance;
    }

    public void initSp(Context mContext) {
        sp = mContext.getSharedPreferences (BuildConfig.SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit ();
    }

    public void putInt(String key, int values) {
        editor.putInt (key, values);
        editor.commit ();
    }

    public int getInt(String key, int defValues) {
        return sp.getInt (key, defValues);
    }

    public void putString(String key, String values) {
        editor.putString (key, values);
        editor.commit ();
    }

    public String getString(String key, String defValues) {
        return sp.getString (key, defValues);
    }

    public void putBoolean(String key, boolean values) {
        editor.putBoolean (key, values);
        editor.commit ();
    }

    public boolean getBoolean(String key, boolean defValues) {
        return sp.getBoolean (key, defValues);
    }

    public void deleteKey(String key) {
        editor.remove (key);
        editor.commit ();
    }

}
