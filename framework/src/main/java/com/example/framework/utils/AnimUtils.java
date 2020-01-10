package com.example.framework.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * FileName:AnimUtils
 * Create Date:2020/1/10 20:27
 * Profile: 动画工具类
 */
public class AnimUtils {

    /**
     * 旋转动画
     * @param view
     * @return
     */
    public static ObjectAnimator rotation(View view){
        ObjectAnimator mAnim=ObjectAnimator.ofFloat (view,"rotation",0f,360f);
        mAnim.setDuration (4*1000);
        mAnim.setRepeatCount (ValueAnimator.INFINITE);
        mAnim.setRepeatMode (ValueAnimator.RESTART);
        mAnim.setInterpolator (new LinearInterpolator ());
        return mAnim;
    }
}
