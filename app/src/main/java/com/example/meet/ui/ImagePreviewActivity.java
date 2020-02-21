package com.example.meet.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.entity.Constants;
import com.example.framework.helper.GlideHelper;
import com.example.meet.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

public class ImagePreviewActivity extends BaseUIActivity implements View.OnClickListener {


    public static void startActivity(Context mContext, boolean isUrl, String url) {
        Intent intent =new Intent (mContext,ImagePreviewActivity.class);
        intent.putExtra (Constants.INTENT_IMAGE_TYPE,isUrl);
        intent.putExtra (Constants.INTENT_IMAGE_URL,url);
        mContext.startActivity (intent);
    }

    private PhotoView photo_view;
    private ImageView iv_back;
    private TextView tv_download;

    //图片地址
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_image_preview);

        initView ();
    }

    private void initView() {
        photo_view = (PhotoView) findViewById (R.id.photo_view);
        iv_back = (ImageView) findViewById (R.id.iv_back);
        tv_download = (TextView) findViewById (R.id.tv_download);

        iv_back.setOnClickListener (this);
        tv_download.setOnClickListener (this);

        Intent intent=getIntent ();
        boolean isUrl=intent.getBooleanExtra (Constants.INTENT_IMAGE_TYPE,false);
        url=intent.getStringExtra (Constants.INTENT_IMAGE_URL);

        if(isUrl){
            GlideHelper.loadUrl (this,url,photo_view);
        }else {
            GlideHelper.loadFile (this,new File (url),photo_view);
        }
    }

    @Override
    public void onClick(View v) {
       switch (v.getId ()){
           case R.id.iv_back:
               finish();
               break;
       }
    }
}
