package com.example.meet.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework.adapter.CommonAdapter;
import com.example.framework.adapter.CommonViewHolder;
import com.example.framework.base.BaseUIActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.bmob.IMUser;
import com.example.framework.entity.Constants;
import com.example.framework.helper.GlideHelper;
import com.example.framework.utils.CommonUtils;
import com.example.framework.view.DialogView;
import com.example.meet.R;
import com.example.meet.model.UserInfoModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseUIActivity implements View.OnClickListener {


    private DialogView mAddFriendDialogView;
    private EditText et_msg;
    private TextView tv_cancel;
    private TextView tv_add_friend;

    private RelativeLayout ll_back;

    private CircleImageView iv_user_photo;
    private TextView tv_nickname;
    private TextView tv_desc;

    private RecyclerView mUserInfoView;
    private CommonAdapter<UserInfoModel> mUserInfoAdapter;
    private List<UserInfoModel> mUserInfoList = new ArrayList<> ();

    private Button btn_add_friend;
    private Button btn_chat;
    private Button btn_audio_chat;
    private Button btn_video_chat;

    private LinearLayout ll_is_friend;

    //个人信息颜色
    private int[] mColor = {0x881E90FF, 0x8800FF7F, 0x88FFD700, 0x88FF6347, 0x88F08080, 0x8840E0D0};

    //用户ID
    private String userId = "";

    private IMUser imUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_info);
        initView ();
    }

    /**
     * 跳转
     *
     * @param mContext
     * @param userId
     */
    public static void startActivity(Context mContext, String userId) {
        Intent intent = new Intent (mContext, UserInfoActivity.class);
        intent.putExtra (Constants.INTENT_USER_ID, userId);
        mContext.startActivity (intent);
    }

    private void initView() {

        userId = getIntent ().getStringExtra (Constants.INTENT_USER_ID);
        ll_back = (RelativeLayout) findViewById (R.id.ll_back);
        iv_user_photo = (CircleImageView) findViewById (R.id.iv_user_photo);
        tv_nickname = (TextView) findViewById (R.id.tv_nickname);
        tv_desc = (TextView) findViewById (R.id.tv_desc);
        mUserInfoView = (RecyclerView) findViewById (R.id.mUserInfoView);
        btn_add_friend = (Button) findViewById (R.id.btn_add_friend);
        btn_chat = (Button) findViewById (R.id.btn_chat);
        btn_audio_chat = (Button) findViewById (R.id.btn_audio_chat);
        btn_video_chat = (Button) findViewById (R.id.btn_video_chat);
        ll_is_friend = (LinearLayout) findViewById (R.id.ll_is_friend);

        ll_back.setOnClickListener (this);
        btn_add_friend.setOnClickListener (this);
        btn_chat.setOnClickListener (this);
        btn_audio_chat.setOnClickListener (this);
        btn_video_chat.setOnClickListener (this);
        iv_user_photo.setOnClickListener (this);

        mUserInfoAdapter = new CommonAdapter<> (mUserInfoList, new CommonAdapter.OnBindDataListener<UserInfoModel> () {
            @Override
            public void onBindViewHolder(UserInfoModel model, CommonViewHolder viewHolder, int type, int position) {
                //viewHolder.setBackgroundColor(R.id.ll_bg, model.getBgColor());
                viewHolder.getView (R.id.ll_bg).setBackgroundColor (model.getBgColor ());
                viewHolder.setText (R.id.tv_type, model.getTitle ());
                viewHolder.setText (R.id.tv_content, model.getContent ());
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.layout_user_info_item;
            }
        });
        mUserInfoView.setLayoutManager (new GridLayoutManager (this, 3));
        mUserInfoView.setAdapter (mUserInfoAdapter);

        queryUserInfo ();
    }

    /**
     * 查询用户信息
     */
    private void queryUserInfo() {
        if (TextUtils.isEmpty (userId)) {
            return;
        }
        BmobManager.getInstance ().queryObjectIdUser (userId, new FindListener<IMUser> () {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty (list)) {
                        imUser = list.get (0);
                        updateUserInfo (imUser);
                    }
                }
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param imUser
     */
    private void updateUserInfo(IMUser imUser) {
        //设置基本属性
        GlideHelper.loadUrl (UserInfoActivity.this, imUser.getPhoto (),
                iv_user_photo);
        tv_nickname.setText (imUser.getNickName ());
        tv_desc.setText (imUser.getDesc ());
        //性别 年龄 生日 星座 爱好 单身状态
        addUserInfoModel (mColor[0], getString (R.string.text_me_info_sex), imUser.isSex () ? getString (R.string.text_me_info_boy) : getString (R.string.text_me_info_girl));
        addUserInfoModel (mColor[1], getString (R.string.text_me_info_age), imUser.getAge () + getString (R.string.text_search_age));
        addUserInfoModel (mColor[2], getString (R.string.text_me_info_birthday), imUser.getBirthday ());
        addUserInfoModel (mColor[3], getString (R.string.text_me_info_constellation), imUser.getConstellation ());
        addUserInfoModel (mColor[4], getString (R.string.text_me_info_hobby), imUser.getHobby ());
        addUserInfoModel (mColor[5], getString (R.string.text_me_info_status), imUser.getStatus ());
        //刷新数据
        Toast.makeText (this, imUser.toString (), Toast.LENGTH_SHORT).show ();
        mUserInfoAdapter.notifyDataSetChanged ();
    }

    private void addUserInfoModel(int color, String title, String content) {
        UserInfoModel model = new UserInfoModel ();
        model.setBgColor (color);
        model.setTitle (title);
        model.setContent (content);
        mUserInfoList.add (model);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {

        }
    }
}
