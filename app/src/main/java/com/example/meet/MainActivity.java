package com.example.meet;

import android.os.Build;
import android.os.Bundle;

import com.example.framework.base.BaseUIActivity;

import androidx.annotation.RequiresApi;


public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

    }

}
