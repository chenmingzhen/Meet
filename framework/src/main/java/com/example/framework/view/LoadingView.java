package com.example.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framework.R;
import com.example.framework.manager.DialogManager;
import com.example.framework.utils.AnimUtils;

/**
 * FileName:LoadingView
 * Create Date:2020/1/15 17:42
 * Profile:加载提示框
 */
public class LoadingView {
   private DialogView mLoadingView;
   private ImageView iv_loading;
   private TextView tv_loading_text;
   private ObjectAnimator mAnim;
   private Context mContext;

    public LoadingView(Context mContext) {
        this.mContext = mContext;
        mLoadingView= DialogManager.getInstance ().initView (mContext, R.layout.dialog_loading);
        iv_loading=mLoadingView.findViewById (R.id.iv_loading);
        tv_loading_text=mLoadingView.findViewById (R.id.tv_loading_text);
        mAnim= AnimUtils.rotation (iv_loading);
    }

    /**
     * 设置加载的提示文本
     * @param text  文本
     */
    public void setLoadingText(String text){
      if(!TextUtils.isEmpty (text)){
          tv_loading_text.setText (text);
      }
    }

    public void show(){
        mAnim.start ();
        DialogManager.getInstance ().show (mLoadingView);
    }

    public void show(String text){
        mAnim.start ();
        setLoadingText (text);
        DialogManager.getInstance ().show (mLoadingView);
    }

    public void hide(){
        mAnim.pause ();
        DialogManager.getInstance ().hide (mLoadingView);
    }

    /**
     * 设置点击外部是否可以取消
     * @param flag
     */
    public void setCancelable(boolean flag){
        mLoadingView.setCancelable (flag);
    }

}
