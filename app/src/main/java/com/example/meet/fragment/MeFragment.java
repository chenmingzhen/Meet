package com.example.meet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framework.base.BaseFragment;
import com.example.meet.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName:MeFragment
 * Create Date:2020/1/16 16:53
 * Profile:
 */
public class MeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_me,null);
        return view;
    }
}