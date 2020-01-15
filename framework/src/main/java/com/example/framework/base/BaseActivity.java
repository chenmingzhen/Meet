package com.example.framework.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * FileName:BaseActivity
 * Create Date:2020/1/10 8:35
 * Profile:
 * -BaseActivity:所有类的统一功能:语言切换，请求权限
 * ---BaseUIActivity:单一功能:沉浸式
 * ---BaseBackActivity:返回键
 * ---****
 */
public class BaseActivity extends AppCompatActivity {

    //申请运行时的权限的Code
    private static final int PERMISSION_REQUEST_CODE = 1000;
    //申请窗口权限的COde
    public static final int PERMISSION_WINDOW_REQUEST_CODE = 1001;

    //申请所需的权限
    private String[] mStrPermission = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION

    };

    //保存没有同意权限
    private List<String> mPerList = new ArrayList<> ();
    //保存没有同意的失败权限
    private List<String> mPerNoList = new ArrayList<> ();

    private OnPermissionsResult permissionsResult;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

    }

    /**
     * 一个方法请求权限
     * @param permissionsResult
     */
    protected void request(OnPermissionsResult permissionsResult){
      if(!checkPermissionALl ()){
          requestPermissionAll (permissionsResult);
      }
    }


    /**
     * 判断单个权限
     * @param permissions
     * @return
     */
    protected boolean checkPermissions(String permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            return checkSelfPermission (permissions)==PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * 判断是否需要申请权限
     * @return
     */
    protected boolean checkPermissionALl(){
        mPerList.clear ();
        for(int i=0;i<mStrPermission.length;i++){
            boolean check= checkPermissions (mStrPermission[i]);
            //如果不同意则请求
            if(!check){
               mPerList.add (mStrPermission[i]);
            }
        }
        return mPerList.size ()>0?false:true;
    }

    /**
     * 申请权限
     * @param mPermissions
     */
    protected void requestPermission(String[] mPermissions){
      if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
          requestPermissions (mPermissions,PERMISSION_REQUEST_CODE);
      }
    }

    /**
     * 申请所有权限
     * @param permissionsResult
     */
    protected void requestPermissionAll(OnPermissionsResult permissionsResult){
        this.permissionsResult=permissionsResult;
        requestPermission ((String[])mPerList.toArray (new String[mPerList.size ()]));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        mPerList.clear ();
        if(requestCode==PERMISSION_REQUEST_CODE){
            if(grantResults.length>0){
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]== PackageManager.PERMISSION_DENIED){
                        //有失败的权限
                        mPerNoList.add(permissions[i]);
                    }
                    if(permissionsResult!=null){
                      if(mPerNoList.size ()==0){
                          permissionsResult.OnSuccess ();
                      }else {
                          permissionsResult.OnFail (mPerNoList);
                      }
                    }
                }
            }
        }
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }

    protected boolean checkWindowPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            return Settings.canDrawOverlays (this);
        }
        return true;
    }

    protected  void requestWindowPermissions(){
        Toast.makeText(this, "申请窗口权限，暂时没做UI交互", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse ("package:"+getPackageName ()));
        startActivityForResult (intent,PERMISSION_WINDOW_REQUEST_CODE);
    }

    public interface OnPermissionsResult{
        void OnSuccess();
        void OnFail(List<String> noPermissions);
    }


}
