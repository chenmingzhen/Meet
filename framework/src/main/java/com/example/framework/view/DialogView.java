package com.example.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * FileName:DialogView
 * Create Date:2020/1/15 12:15
 * Profile: 自定义提示框
 */
public class DialogView extends Dialog {

    /**
     *
     * @param mContext 上下文
     * @param layout  布局文件
     * @param style  Dialog风格
     * @param gravity  重心
     */
    public DialogView(Context mContext, int layout, int style, int gravity) {
        super (mContext, style);
        setContentView (layout);
        Window window = getWindow ();
        WindowManager.LayoutParams layoutParams = window.getAttributes ();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes (layoutParams);
    }
}
