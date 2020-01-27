package com.example.framework.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName:CommonAdapter
 * Create Date:2020/1/27 11:37
 * Profile:万能适配器
 */
public class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {


    private List<T> mList;

    private OnBindDataListener<T> onBindDataListener;
    private OnMoreBindDataListener<T> onMoreBindDataListener;

    public CommonAdapter(List<T> mList, OnBindDataListener<T> onBindDataListener) {
        this.mList = mList;
        this.onBindDataListener = onBindDataListener;
    }

    public CommonAdapter(List<T> mList, OnMoreBindDataListener<T> onMoreBindDataListener) {
        this.mList = mList;
        this.onBindDataListener =onMoreBindDataListener;
        this.onMoreBindDataListener = onMoreBindDataListener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int LayoutId=onBindDataListener.getLayoutId (viewType);
        CommonViewHolder viewHolder=CommonViewHolder.getViewHolder (parent,LayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        onBindDataListener.onBindViewHolder (mList.get (position),holder,getItemViewType (position),position);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size ();
    }

    @Override
    public int getItemViewType(int position) {
        if(onMoreBindDataListener!=null){
            return onMoreBindDataListener.getItemType (position);
        }
        return 0;
    }

    //绑定数据
    public interface OnBindDataListener<T> {
        void onBindViewHolder(T model, CommonViewHolder viewHolder, int type, int position);

        int getLayoutId(int type);
    }

    //绑定多类型的数据
    public interface OnMoreBindDataListener<T> extends OnBindDataListener<T>{
        int getItemType(int position);
    }
}
