package com.example.meet;

import android.os.Build;
import android.os.Bundle;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.utils.LogUtils;

import java.util.List;

import androidx.annotation.RequiresApi;


public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        init();
    }

    private void init() {
        requestPermission();
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        request (new OnPermissionsResult () {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnFail(List<String> noPermissions) {
                LogUtils.i ("noPermissions:"+noPermissions.toString ());
            }
        });
    }

}
