package com.example.framework.manager;

import android.content.Context;
import android.view.Gravity;

import com.example.framework.R;
import com.example.framework.view.DialogView;

/**
 * FileName:DialogManager
 * Create Date:2020/1/15 16:11
 * Profile: 提示框管理类
 */
public class DialogManager {
    private volatile static DialogManager mInstance=null;

    private DialogManager(){

    }

    public static DialogManager getInstance(){
        if(mInstance==null){
            synchronized (DialogManager.class){
                if(mInstance==null){
                    mInstance=new DialogManager ();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context mContext,int layout){
        return new DialogView (mContext,layout, R.style.Theme_Dialog, Gravity.CENTER);
    }
    public DialogView initView(Context mContext,int layout,int gravity){
        return new DialogView (mContext,layout, R.style.Theme_Dialog, gravity);
    }

    public void show(DialogView view){
        if(view !=null){
            if(!view.isShowing ()){
                view.show ();
            }
        }
    }

    public void hide(DialogView view){
        if(view!=null){
            if(view.isShowing ()){
                view.dismiss ();
            }
        }
    }
}
