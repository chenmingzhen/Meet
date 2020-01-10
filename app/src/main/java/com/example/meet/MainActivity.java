package com.example.meet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.manager.MediaPlayerManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        //检查权限
        checkPermission ();
    }

    private void checkPermission() {
        //申请权限  注意 要两个权限都申请
        if (ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions (MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText (MainActivity.this, "成功获得读取文件权限", Toast.LENGTH_SHORT).show ();
                } else {
                    Toast.makeText (MainActivity.this, "没有获得读取文件权限", Toast.LENGTH_SHORT).show ();
                }
                break;
            default:
                break;
        }
    }
}
