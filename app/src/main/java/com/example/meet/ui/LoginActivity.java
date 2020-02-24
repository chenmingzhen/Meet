package com.example.meet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework.base.BaseUIActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.bmob.IMUser;
import com.example.framework.entity.Constants;
import com.example.framework.manager.DialogManager;
import com.example.framework.utils.LogUtils;
import com.example.framework.utils.SpUtils;
import com.example.framework.view.DialogView;
import com.example.framework.view.LoadingView;
import com.example.framework.view.TouchPictureV;
import com.example.meet.MainActivity;
import com.example.meet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseUIActivity implements View.OnClickListener {

    /**
     * 1.点击发送的按钮，弹出一个提示框，图片验证码，验证通过之后
     * 2.!发送验证码，@同时按钮变成不可点击，@按钮开始倒计时，倒计时结束，@按钮可点击，@文字变成“发送”
     * 3.通过手机号码和验证码进行登录
     * 4.登录成功之后获取本地对象
     */

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.btn_send_code)
    Button btn_send_code;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_test_login)
    TextView tv_test_login;

    private DialogView mCodeView;
    private TouchPictureV mPictureV;
    private LoadingView mLoadingView;

    private static final int H_TIME = 1001;
    private static int TIME = 60;
    private Handler mHandler = new Handler (new Handler.Callback () {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case H_TIME:
                    TIME--;
                    btn_send_code.setText (TIME + "s");
                    if (TIME > 0) {
                        mHandler.sendEmptyMessageDelayed (H_TIME, 1000);
                    } else {
                        btn_send_code.setEnabled (true);
                        btn_send_code.setText (getString (R.string.text_login_send));
                        TIME = 60;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        ButterKnife.bind (this);
        init ();
    }

    /**
     * 初始化
     */
    private void init() {
        btn_login.setOnClickListener (this);
        btn_send_code.setOnClickListener (this);
        tv_test_login.setOnClickListener (this);
        et_phone.setText (SpUtils.getInstance ().getString (Constants.SP_PHONE, ""));

        mLoadingView = new LoadingView (this);
        //通过mCodeView找到自定义拖拽框
        mCodeView = DialogManager.getInstance ().initView (this, R.layout.dialog_code_view);
        mPictureV = mCodeView.findViewById (R.id.mPicture);
        mPictureV.setViewResultListener (new TouchPictureV.OnViewResultListener () {
            @Override
            public void onResult() {
                DialogManager.getInstance ().hide (mCodeView);
                //原本这里是执行下面注释代码
                //sendSMS()
                final IMUser user = new IMUser ();
                //此处替换为你的用户名
                user.setUsername ("12510603000");
                //此处替换为你的密码
                user.setPassword ("123456");
                user.login (new SaveListener<IMUser> () {
                    @Override
                    public void done(IMUser bmobUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText (LoginActivity.this, "登录成功：" + BmobManager.getInstance ().getUser ().getUsername (), Toast.LENGTH_LONG).show ();
                            startActivity (new Intent (LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText (LoginActivity.this, "登录失败：" + e.getMessage (), Toast.LENGTH_LONG).show ();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.btn_send_code:
                DialogManager.getInstance ().show (mCodeView);
                break;
            case R.id.btn_login:
                login ();
                break;
            case R.id.tv_test_login:
                DialogManager.getInstance ().show (mCodeView);
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        final String phone = et_phone.getText ().toString ().trim ();
        if (TextUtils.isEmpty (phone)) {
            Toast.makeText (this, getString (R.string.text_login_phone_null),
                    Toast.LENGTH_SHORT).show ();
            return;
        }
        String code = et_code.getText ().toString ().trim ();
        if (TextUtils.isEmpty (code)) {
            Toast.makeText (this, getString (R.string.text_login_code_null),
                    Toast.LENGTH_SHORT).show ();
            return;
        }

        //显示LoadingView
        mLoadingView.show (getString (R.string.text_login_now_login_text));
        //调用Bmob云函数登陆
        BmobManager.getInstance ().signOrLoginByMobilePhone (phone, code, new LogInListener<IMUser> () {
            @Override
            public void done(IMUser imUser, BmobException e) {
                mLoadingView.hide ();
                if (e == null) {
                    startActivity (new Intent (LoginActivity.this, MainActivity.class));
                    SpUtils.getInstance ().putString (Constants.SP_PHONE, phone);
                    finish ();
                } else {
                    if (e.getErrorCode () == 207) {
                        Toast.makeText (LoginActivity.this, getString (R.string.text_login_code_error), Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (LoginActivity.this, "ERROR:" + e.toString (), Toast.LENGTH_SHORT).show ();
                    }
                }
            }
        });
    }

    /**
     * 发送短信
     */
    private void sendSMS() {
        //1.获取手机号码
        String phone = et_phone.getText ().toString ().trim ();
        if (TextUtils.isEmpty (phone)) {
            Toast.makeText (this, getString (R.string.text_login_phone_null), Toast.LENGTH_SHORT).show ();
            return;
        }
        //2.请求短信验证码
        BmobManager.getInstance ().requestSMS (phone, new QueryListener<Integer> () {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    btn_send_code.setEnabled (false);
                    mHandler.sendEmptyMessage (H_TIME);
                    Toast.makeText (LoginActivity.this, getString (R.string.text_user_resuest_succeed), Toast.LENGTH_SHORT).show ();
                } else {
                    LogUtils.e ("SMS:" + e.toString ());
                    Toast.makeText (LoginActivity.this, getString (R.string.text_user_resuest_fail), Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }

}
