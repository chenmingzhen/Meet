package com.example.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * FileName:BmobManager
 * Create Date:2020/1/14 18:28
 * Profile:
 */
public class BmobManager {

    private final static String BMOB_SDK_ID = "b85cc2bd7b20f60bf7e94a68855b01c9";

    public volatile static BmobManager mInstance = null;

    private BmobManager() {

    }

    public static BmobManager getInstance() {
        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager ();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取本地对象
     * @return
     */
    public IMUser getUser(){
        return BmobUser.getCurrentUser (IMUser.class);
    }

    /**
     * 初始化Bmob
     *
     * @param context
     */
    public void initBmob(Context context) {
        Bmob.initialize (context, BMOB_SDK_ID);
    }

    /**
     * 发送短信验证码
     *
     * @param phone    手机号
     * @param listener 回调
     */
    public void requestSMS(String phone, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode (phone, "", listener);
    }

    /**
     * 通过手机号码注册或者登录
     * @param phone 手机号
     * @param code  验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener)
    {
        BmobUser.signOrLoginByMobilePhone (phone,code,listener);
    }
}
