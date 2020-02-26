package com.example.framework.manager;

import android.content.Context;

import com.example.framework.utils.LogUtils;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class VoiceManager {

    private static volatile VoiceManager singleton;

    private RecognizerDialog mIatDialog;

    private VoiceManager(Context mContext) {
        SpeechUtility.createUtility (mContext, SpeechConstant.APPID + "=5b18db70");
        mIatDialog = new RecognizerDialog (mContext, new InitListener () {
            @Override
            public void onInit(int i) {
                LogUtils.i ("InitListener:" + i);
            }
        });
        //清空所有属性
        mIatDialog.setParameter (SpeechConstant.CLOUD_GRAMMAR, null);
        mIatDialog.setParameter (SpeechConstant.SUBJECT, null);
        //设置返回格式
        mIatDialog.setParameter (SpeechConstant.RESULT_TYPE, "json");
        //设置在线引擎
        mIatDialog.setParameter (SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置语言
        mIatDialog.setParameter (SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mIatDialog.setParameter (SpeechConstant.ACCENT, "mandarin");
        //4s没说话默认此次操作结束
        mIatDialog.setParameter (SpeechConstant.VAD_BOS, "4000");
        //静音超时
        mIatDialog.setParameter (SpeechConstant.VAD_EOS, "1000");
        mIatDialog.setParameter (SpeechConstant.ASR_PTT, "1");
    }

    public static VoiceManager getInstance(Context mContext) {
        if (singleton == null) {
            synchronized (VoiceManager.class) {
                if (singleton == null) {
                    singleton = new VoiceManager (mContext);
                }
            }
        }
        return singleton;
    }


    public void startSpeak(RecognizerDialogListener listener){
        mIatDialog.setListener (listener);
        mIatDialog.show ();
    }
}