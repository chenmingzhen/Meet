package com.example.meet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework.base.BaseFragment;
import com.example.meet.R;
import com.example.meet.adapter.CloudTagAdapter;
import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName:StarFragment
 * Create Date:2020/1/16 16:58
 * Profile: 星球
 */
public class StarFragment extends BaseFragment implements View.OnClickListener {
    /*    @BindView(R.id.tv_start_title)
        TextView tv_start_title;
        @BindView(R.id.iv_camera)
        ImageView iv_camera;
        @BindView(R.id.iv_add)
        ImageView iv_add;
        @BindView(R.id.tv_connect_status)
        TextView tv_connect_status;
        @BindView(R.id.mCloudView)
        TagCloudView mCloudView;
        @BindView(R.id.tv_random)
        TextView tv_random;
        @BindView(R.id.ll_random)
        LinearLayout ll_random;
        @BindView(R.id.tv_soul)
        TextView tv_soul;
        @BindView(R.id.ll_soul)
        LinearLayout ll_soul;
        @BindView(R.id.tv_fate)
        TextView tv_fate;
        @BindView(R.id.ll_fate)
        LinearLayout ll_fate;
        @BindView(R.id.tv_love)
        TextView tv_love;
        @BindView(R.id.ll_love)
        LinearLayout ll_love;*/
    //二维码结果
    private static final int REQUEST_CODE = 1235;

    private TextView tv_star_title;
    private ImageView iv_camera;
    private ImageView iv_add;

    private TagCloudView mCloudView;

    private LinearLayout ll_random;
    private LinearLayout ll_soul;
    private LinearLayout ll_fate;
    private LinearLayout ll_love;

    //连接状态
    private TextView tv_connect_status;

    private List<String> mStarList = new ArrayList<> ();
    private CloudTagAdapter mCloudTagAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_star, null);
        initView (view);
        return view;
    }

    private void initView(View view) {
        iv_camera = view.findViewById(R.id.iv_camera);
        iv_add = view.findViewById(R.id.iv_add);
        tv_connect_status = view.findViewById(R.id.tv_connect_status);

        tv_star_title = view.findViewById(R.id.tv_star_title);

        mCloudView = view.findViewById(R.id.mCloudView);

        ll_random = view.findViewById(R.id.ll_random);
        ll_soul = view.findViewById(R.id.ll_soul);
        ll_fate = view.findViewById(R.id.ll_fate);
        ll_love = view.findViewById(R.id.ll_love);

        iv_camera.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        ll_random.setOnClickListener(this);
        ll_soul.setOnClickListener(this);
        ll_fate.setOnClickListener(this);
        ll_love.setOnClickListener(this);

        ll_random.setOnClickListener (this);
        ll_soul.setOnClickListener (this);
        ll_fate.setOnClickListener (this);
        ll_love.setOnClickListener (this);
        for (int i = 0; i < 100; i++) {
            mStarList.add ("Star:" + i);
        }

        //数据绑定
        mCloudTagAdapter = new CloudTagAdapter (getActivity (), mStarList);
        mCloudView.setAdapter (mCloudTagAdapter);

        //监听点击事件
        mCloudView.setOnTagClickListener (new TagCloudView.OnTagClickListener () {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Toast.makeText (getContext (), mStarList.get (position), Toast.LENGTH_SHORT).show ();
            }
        });
    }

    @Override
    public void onClick(View v) {
        
    }
}
