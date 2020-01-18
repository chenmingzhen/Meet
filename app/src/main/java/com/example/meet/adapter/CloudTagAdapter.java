package com.example.meet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meet.R;
import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

/**
 * FileName:CloudTagAdapter
 * Create Date:2020/1/17 11:20
 * Profile: 3D星球适配器
 */
public class CloudTagAdapter extends TagsAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public CloudTagAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater=(LayoutInflater)mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size ();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        //初始化View
        View view =inflater.inflate (R.layout.layout_star_view_item,null);
        //初始化控件
        ImageView iv_star_icon=view.findViewById (R.id.iv_star_icon);
        TextView tv_star_name=view.findViewById (R.id.tv_star_name);

        //赋值
        tv_star_name.setText (mList.get (position));
        iv_star_icon.setImageResource (R.drawable.img_star_icon_3);
        return view;
    }

    @Override
    public Object getItem(int position) {
        return mList.get (position);
    }

    @Override
    public int getPopularity(int position) {
        return 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}
