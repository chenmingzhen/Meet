package com.example.meet.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.framework.base.BaseBackActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.bmob.IMUser;
import com.example.framework.utils.CommonUtils;
import com.example.meet.R;
import com.example.meet.adapter.AddFriendAdapter;
import com.example.meet.model.AddFriendModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddFriendActivity extends BaseBackActivity implements View.OnClickListener {

    //标题
    public static final int TYPE_TITLE = 0;
    //内容
    public static final int TYPE_CONTENT = 1;

    private View include_empty_view;

    @BindView(R.id.ll_to_contact)
    LinearLayout ll_to_contact;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.mSearchResultView)
    RecyclerView mSearchResult;

    private AddFriendAdapter mAddFriendAdapter;
    private List<AddFriendModel> mList = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_friend);
        initView ();

    }

    private void initView() {
        ButterKnife.bind (this);
        include_empty_view = findViewById (R.id.include_empty_view);
        ll_to_contact.setOnClickListener (this);
        iv_search.setOnClickListener (this);


        mSearchResult.setLayoutManager (new LinearLayoutManager (this));
        mSearchResult.addItemDecoration (new DividerItemDecoration (this, DividerItemDecoration.VERTICAL));
        mAddFriendAdapter = new AddFriendAdapter (this, mList);
        mSearchResult.setAdapter (mAddFriendAdapter);

        mAddFriendAdapter.setOnClickListener (new AddFriendAdapter.OnClickListener () {
            @Override
            public void OnClick(int position) {
                Toast.makeText (AddFriendActivity.this, "position:" + position, Toast.LENGTH_SHORT).show ();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            //跳转到通讯录导入
            case R.id.ll_to_contact:
                //处理权限
                if (checkPermissions (Manifest.permission.READ_CONTACTS)) {
                    startActivity (new Intent(this,ContactFriendActivity.class));
                } else {
                   requestPermission (new String[]{Manifest.permission.READ_CONTACTS});
                }
                break;
            case R.id.iv_search:
                QueryPhoneUser ();
                break;
        }
    }

    /**
     * 根据手机号码查询
     */
    private void QueryPhoneUser() {
        //1.获取电话号码
        String phone = et_phone.getText ().toString ().trim ();
        if (TextUtils.isEmpty (phone)) {
            Toast.makeText (this, getString (R.string.text_login_phone_null),
                    Toast.LENGTH_SHORT).show ();
            return;
        }

        //2.过滤自己
        String phoneNumber = BmobManager.getInstance ().getUser ().getMobilePhoneNumber ();
        if (phone.equals (phoneNumber)) {
            Toast.makeText (this, getString (R.string.text_add_friend_no_me), Toast.LENGTH_SHORT).show ();
            return;
        }

        //3.查询
        BmobManager.getInstance ().queryPhoneUser (phone, new FindListener<IMUser> () {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e != null) return;
                if (CommonUtils.isEmpty (list)) {
                    IMUser imUser = list.get (0);
                    include_empty_view.setVisibility (View.GONE);
                    mSearchResult.setVisibility (View.VISIBLE);
                    //每次查询有数据则清除
                    mList.clear ();
                    //通知适配器数据发生改变
                    mAddFriendAdapter.notifyDataSetChanged ();

                    addTitle ("查询结果");
                    addContent (imUser);

                    //推荐
                    pushUser ();
                } else {
                    //显示空数据
                    include_empty_view.setVisibility (View.VISIBLE);
                    mSearchResult.setVisibility (View.GONE);
                }
            }
        });
    }

    /**
     * 推荐好友
     */
    private void pushUser() {
        //查询所有好友 100
        BmobManager.getInstance ().queryAllUser (new FindListener<IMUser> () {
            @Override
            public void done(List<IMUser> list, BmobException e) {
                if (e == null) {
                    if (CommonUtils.isEmpty (list)) {
                        addTitle ("推荐好友");
                        int num = (list.size () <= 100) ? list.size () : 100;
                        for (int i = 0; i < num; i++) {
                            String phoneNumber = BmobManager.getInstance ().getUser ().getMobilePhoneNumber ();
                            if (list.get (i).getMobilePhoneNumber ().equals (phoneNumber)) {
                                continue;
                            }
                            addContent (list.get (i));
                        }
                        mAddFriendAdapter.notifyDataSetChanged ();
                    }
                }
            }
        });
    }

    /**
     * 添加头部
     *
     * @param title
     */
    private void addTitle(String title) {
        AddFriendModel model = new AddFriendModel ();
        model.setTitle (title);
        model.setType (AddFriendAdapter.TYPE_TITLE);
        mList.add (model);
    }

    /**
     * 添加内容
     *
     * @param imUser
     */
    private void addContent(IMUser imUser) {
        AddFriendModel model = new AddFriendModel ();
        model.setType (AddFriendAdapter.TYPE_CONTENT);
        model.setUserId (imUser.getObjectId ());
        model.setPhoto (imUser.getPhoto ());
        model.setSex (imUser.isSex ());
        model.setAge (imUser.getAge ());
        model.setNickName (imUser.getNickName ());
        model.setDesc (imUser.getDesc ());
        mList.add (model);
    }
}
