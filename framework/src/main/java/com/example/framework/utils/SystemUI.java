package com.example.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;


/**
 * 沉浸式实现
 */
public class SystemUI {

    public static void fixSystemUI(Activity mActivity) {

        //沉浸式状态栏
        //5.0适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //获取最顶层的View
            mActivity.getWindow ().getDecorView ().setSystemUiVisibility (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            //状态栏透明
            mActivity.getWindow ().setStatusBarColor (Color.TRANSPARENT);
        }
    }
}
