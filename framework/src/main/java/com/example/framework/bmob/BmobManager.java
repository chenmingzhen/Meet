package com.example.framework.bmob;

import android.content.Context;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

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
     *
     * @return
     */
    public IMUser getUser() {
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
     *
     * @param phone    手机号
     * @param code     验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener) {
        BmobUser.signOrLoginByMobilePhone (phone, code, listener);
    }

    public boolean isLogin() {
        return BmobUser.isLogin ();
    }

    public void uploadFirstPhoto(final String nickName, File file, final OnUploadPhotoListener onUploadPhotoListener) {
        /**
         * 1.上传文件拿到地址
         * 2.更新用户信息
         */
        final IMUser imUser = getUser ();
        final BmobFile bmobFile = new BmobFile (file);
        bmobFile.uploadblock (new UploadFileListener () {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    imUser.setNickName (nickName);
                    imUser.setPhoto (bmobFile.getFileUrl ());

                    imUser.setTokenNickName (nickName);
                    imUser.setTokenPhoto (nickName);

                    //更新用户信息
                    imUser.update (new UpdateListener () {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                onUploadPhotoListener.OnUpdateDone ();
                            } else {
                                onUploadPhotoListener.OnUpdateFail (e);
                            }
                        }
                    });
                } else {
                    onUploadPhotoListener.OnUpdateFail (e);
                }
            }
        });
    }


    public interface OnUploadPhotoListener {

        void OnUpdateDone();

        void OnUpdateFail(BmobException e);
    }

    public void queryPhoneUser(String phone, FindListener<IMUser> listener){
        baseQuery("mobilePhoneNumber", phone, listener);
    }

    public void queryAllUser(FindListener<IMUser> listener){
        BmobQuery<IMUser> query=new BmobQuery<> ();
        query.findObjects (listener);
    }

    /**
     * 查询基类
     * @param key
     * @param values
     * @param listener
     */
    public void baseQuery(String key,String values,FindListener<IMUser> listener){
        BmobQuery<IMUser> query=new BmobQuery<> ();
        query.addWhereEqualTo (key,values);
        query.findObjects (listener);
    }
}
