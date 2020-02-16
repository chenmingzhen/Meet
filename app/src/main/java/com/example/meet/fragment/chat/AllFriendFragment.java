package com.example.meet.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framework.base.BaseFragment;
import com.example.meet.R;


/**
 * FileName:AllFriendFragment
 * Create Date:2020/2/12 11:22
 * Profile: 所有联系人
 */
public class AllFriendFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_all_record,null);
        return view;
    }
}
