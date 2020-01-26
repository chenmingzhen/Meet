package com.example.meet.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.framework.base.BaseBackActivity;
import com.example.framework.bmob.BmobManager;
import com.example.framework.bmob.IMUser;
import com.example.framework.utils.CommonUtils;
import com.example.framework.utils.LogUtils;
import com.example.meet.R;
import com.example.meet.adapter.AddFriendAdapter;
import com.example.meet.model.AddFriendModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactFriendActivity extends BaseBackActivity {

    @BindView(R.id.mContactView)
    RecyclerView mContactView;

    private Map<String, String> mContactMap = new HashMap<> ();

    private AddFriendAdapter mContactAdapter;
    private List<AddFriendModel> mList = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_contact_firend);
        ButterKnife.bind (this);
        initView ();
    }

    private void initView() {
        mContactView.setLayoutManager (new LinearLayoutManager (this));
        mContactView.addItemDecoration (new DividerItemDecoration (this, DividerItemDecoration.VERTICAL));
        mContactAdapter=new AddFriendAdapter (this,mList);
        mContactView.setAdapter (mContactAdapter);

        loadContact ();
        
        loadUser ();

    }

    /**
     * 加载用户
     */
    private void loadUser() {
        if(mContactMap.size ()>0){
            for(final Map.Entry<String,String> entry:mContactMap.entrySet ()){
                BmobManager.getInstance ().queryPhoneUser (entry.getValue (), new FindListener<IMUser> () {
                    @Override
                    public void done(List<IMUser> list, BmobException e) {
                        if(e==null){
                            if(CommonUtils.isEmpty (list)){
                                IMUser imUser=list.get (0);
                                addContent (imUser,entry.getKey (),entry.getValue ());
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 加载联系人
     */
    private void loadContact() {
        Cursor cursor = getContentResolver ().query (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String name, phone;
        while (cursor.moveToNext ()) {
            name = cursor.getString (cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone = cursor.getString (cursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER));
            LogUtils.i ("name:" + name + " phone:" + phone);
            phone = phone.replace (" ", "").replace ("-", "");
            mContactMap.put (name,phone);
        }
    }

    /**
     * 添加内容
     *
     * @param imUser
     */
    private void addContent(IMUser imUser, String name, String phone) {
        AddFriendModel model = new AddFriendModel();
        model.setType(AddFriendActivity.TYPE_CONTENT);
        model.setUserId(imUser.getObjectId());
        model.setPhoto(imUser.getPhoto());
        model.setSex(imUser.isSex());
        model.setAge(imUser.getAge());
        model.setNickName(imUser.getNickName());
        model.setDesc(imUser.getDesc());

        model.setContact(true);
        model.setContactName(name);
        model.setContactPhone(phone);

        mList.add(model);
        mContactAdapter.notifyDataSetChanged();
    }
}
