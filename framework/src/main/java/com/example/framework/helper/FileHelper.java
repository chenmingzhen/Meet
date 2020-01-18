package com.example.framework.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.content.FileProvider;

/**
 * FileName:FileHelper
 * Create Date:2020/1/17 22:25
 * Profile:
 */
public class FileHelper {

    //相机
    public static final int CAMEAR_REQUEST_CODE = 1004;
    //相册
    public static final int ALBUM_REQUEST_CODE = 1005;
    //音乐
    public static final int MUSIC_REQUEST_CODE = 1006;
    //视频
    public static final int VIDEO_REQUEST_CODE = 1007;

    //裁剪结果
    public static final int CAMERA_CROP_RESULT = 1008;

    private static volatile FileHelper mInstnce = null;
    private File tempFile = null;
    private Uri imageUri;
    private SimpleDateFormat simpleDateFormat;

    //裁剪文件
    private String cropPath;

    private FileHelper() {
        simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    }

    public static FileHelper getInstance() {
        if (mInstnce == null) {
            synchronized (FileHelper.class) {
                if (mInstnce == null) {
                    mInstnce = new FileHelper ();
                }
            }
        }
        return mInstnce;
    }

    /**
     * 如果头像上传，可以支持裁剪，自行增加
     *
     * @param mActivity
     */
    public void toCamera(Activity mActivity) {
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = simpleDateFormat.format (new Date ());
        tempFile = new File (Environment.getExternalStorageDirectory (), fileName + ".jpg");
        //兼容Android N
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile (tempFile);
        } else {
            //利用FileProvider
            //把自己tempFile的Uri提供出去
            imageUri = FileProvider.getUriForFile (mActivity, mActivity.getPackageName () + ".fileprovider", tempFile);
            //添加权限
            intent.addFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION |
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        intent.putExtra (MediaStore.EXTRA_OUTPUT,imageUri);
        mActivity.startActivityForResult (intent,CAMEAR_REQUEST_CODE);
    }

    public File getTempFile(){
        return tempFile;
    }
    
}
