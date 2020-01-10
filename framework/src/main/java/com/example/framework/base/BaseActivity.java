package com.example.framework.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * FileName:BaseActivity
 * Create Date:2020/1/10 8:35
 * Profile:
 *     -BaseActivity:所有类的统一功能:语言切换，请求权限
 *     ---BaseUIActivity:单一功能:沉浸式
 *     ---BaseBackActivity:返回键
 *     ---****
 * 
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //检查权限
        checkPermission ();
    }

    private void checkPermission() {
        //申请权限  注意 要两个权限都申请
        if (ContextCompat.checkSelfPermission (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission
                (this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText (this, "成功获得读取文件权限", Toast.LENGTH_SHORT).show ();
                } else {
                    Toast.makeText (this, "没有获得读取文件权限", Toast.LENGTH_SHORT).show ();
                }
                break;
            default:
                break;
        }
    }
}
