package com.example.framework.base;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * FileName:BaseBackActivity
 * Create Date:2020/1/17 16:58
 * Profile:
 */
public class BaseBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        //显示返回键
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
            //清除阴影
            getSupportActionBar ().setElevation (0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == android.R.id.home) {
            finish ();
        }
        return super.onOptionsItemSelected (item);
    }
}
