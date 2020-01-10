package com.example.framework.base;

import android.os.Bundle;

import com.example.framework.utils.SystemUI;

import androidx.annotation.Nullable;

public class BaseUIActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        SystemUI.fixSystemUI (this);
    }
}
