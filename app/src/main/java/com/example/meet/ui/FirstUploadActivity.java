package com.example.meet.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework.base.BaseBackActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.helper.FileHelper;
import com.example.framework.manager.DialogManager;
import com.example.framework.view.DialogView;
import com.example.framework.view.LoadingView;
import com.example.meet.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 头像上传
 */
public class FirstUploadActivity extends BaseBackActivity implements View.OnClickListener {

    @BindView(R.id.iv_photo)
    CircleImageView iv_photo;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.btn_upload)
    Button btn_upload;

    private File uploadFile = null;

    private TextView tv_camera;
    private TextView tv_ablum;
    private TextView tv_cancel;

    private LoadingView mLoadingView;
    private DialogView mPhotoSelectView;


    public static void startActivity(Activity mActivity) {
        Intent intent = new Intent (mActivity, FirstUploadActivity.class);
        mActivity.startActivity (intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_first_upload);
        ButterKnife.bind (this);
        initView ();
    }

    private void initView() {
        initPhotoView ();

        iv_photo.setOnClickListener (this);
        btn_upload.setOnClickListener (this);

        btn_upload.setEnabled (false);

        et_nickname.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length () > 0) {
                    btn_upload.setEnabled (uploadFile != null);
                } else {
                    btn_upload.setEnabled (false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 初始化选择框
     */
    private void initPhotoView() {
        mLoadingView = new LoadingView (this);
        mLoadingView.setLoadingText (getString (R.string.text_upload_photo_loding));

        mPhotoSelectView = DialogManager.getInstance ().initView (this, R.layout.layout_select_photo, Gravity.BOTTOM);
        tv_camera = (TextView) mPhotoSelectView.findViewById (R.id.tv_camera);
        tv_camera.setOnClickListener (this);
        tv_ablum = (TextView) mPhotoSelectView.findViewById (R.id.tv_album);
        tv_ablum.setOnClickListener (this);
        tv_cancel = (TextView) mPhotoSelectView.findViewById (R.id.tv_cancel);
        tv_cancel.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.tv_camera:
                DialogManager.getInstance ().hide (mPhotoSelectView);
                if (!checkPermissions (Manifest.permission.CAMERA)) {
                    requestPermission (new String[]{Manifest.permission.CAMERA});
                } else {
                    //跳转到相机
                    FileHelper.getInstance ().toCamera (this);
                }
                break;
            case R.id.tv_album:
                DialogManager.getInstance ().hide (mPhotoSelectView);
                //跳转到相册
                FileHelper.getInstance ().toAlbum (this);
                break;
            case R.id.tv_cancel:
                DialogManager.getInstance ().hide (mPhotoSelectView);
                break;
            case R.id.btn_upload:
                uploadPhoto ();
                break;
            case R.id.iv_photo:
                //显示选择提示框
                DialogManager.getInstance ().show (mPhotoSelectView);
                break;
        }
    }

    /**
     * 上传头像
     */
    private void uploadPhoto() {
        String nickName = et_nickname.getText ().toString ().trim ();
        mLoadingView.show ();
        BmobManager.getInstance ().uploadFirstPhoto (nickName, uploadFile, new BmobManager.OnUploadPhotoListener () {
            @Override
            public void OnUpdateDone() {
                mLoadingView.hide ();
                finish ();
            }

            @Override
            public void OnUpdateFail(BmobException e) {
                mLoadingView.hide ();
                Toast.makeText (FirstUploadActivity.this, e.toString (), Toast.LENGTH_SHORT).show ();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case FileHelper.CAMEAR_REQUEST_CODE:
                    uploadFile = FileHelper.getInstance ().getTempFile ();
                    break;
                case FileHelper.ALBUM_REQUEST_CODE:
                    Uri uri = data.getData ();
                    if (uri != null) {
                        //String path=uri.getPath() 非系统绝对路径
                        String path = FileHelper.getInstance ().getRealPathFromURI (FirstUploadActivity.this, uri);
                        if (!TextUtils.isEmpty (path)) {
                            uploadFile = new File (path);
                        }
                    }

                    break;
            }
        }
        //设置头像
        if (uploadFile != null) {
            Bitmap mBitmap = BitmapFactory.decodeFile (uploadFile.getPath ());
            iv_photo.setImageBitmap (mBitmap);

            String nickName = et_nickname.getText ().toString ().trim ();
            btn_upload.setEnabled (!TextUtils.isEmpty (nickName));
        }
        super.onActivityResult (requestCode, resultCode, data);
    }
}
