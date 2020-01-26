package com.example.meet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framework.helper.GlideHelper;
import com.example.meet.R;
import com.example.meet.model.AddFriendModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * FileName:AddFriendAdapter
 * Create Date:2020/1/23 21:13
 * Profile:
 */
public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //标题
    public static final int TYPE_TITLE = 0;
    //内容
    public static final int TYPE_CONTENT = 1;


    private Context mContext;
    private List<AddFriendModel> mList;
    private LayoutInflater inflater;

    //自定义的点击事件接口
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AddFriendAdapter(Context mContext, List<AddFriendModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            return new TitleViewHolder (inflater.inflate (R.layout.layout_search_title_item, null));
        } else if (viewType == TYPE_CONTENT) {
            return new ContentViewHolder (inflater.inflate (R.layout.layout_search_user_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddFriendModel model = mList.get (position);
        if (model.getType () == TYPE_TITLE) {
            ((TitleViewHolder) holder).tv_title.setText (model.getTitle ());
        } else if (model.getType () == TYPE_CONTENT) {
            //设置头像
            GlideHelper.loadUrl (mContext, model.getPhoto (), ((ContentViewHolder) holder).iv_photo);
            //设置性别
            ((ContentViewHolder) holder).iv_sex.setImageResource (model.isSex () ? R.drawable.img_boy_icon : R.drawable.img_girl_icon);
            //设置昵称
            ((ContentViewHolder) holder).tv_nickname.setText (model.getNickName ());
            //设置年龄
            ((ContentViewHolder) holder).tv_desc.setText (model.getDesc ());

            //通讯录中读取
            if (model.isContact ()) {
                ((ContentViewHolder) holder).ll_contact_info.setVisibility (View.VISIBLE);
                ((ContentViewHolder) holder).tv_contact_name.setText (model.getContactName ());
                ((ContentViewHolder) holder).tv_contact_phone.setText (model.getContactPhone ());
            }
        }

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.OnClick (position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size ();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get (position).getType ();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;

        public TitleViewHolder(View itemView) {
            super (itemView);
            tv_title = itemView.findViewById (R.id.tv_title);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView iv_photo;
        private ImageView iv_sex;
        private TextView tv_nickname;
        private TextView tv_age;
        private TextView tv_desc;
        private TextView tv_contact_name;
        private TextView tv_contact_phone;
        private LinearLayout ll_contact_info;

        public ContentViewHolder(View itemView) {
            super (itemView);
            iv_photo = itemView.findViewById (R.id.iv_photo);
            iv_sex = itemView.findViewById (R.id.iv_sex);
            tv_nickname = itemView.findViewById (R.id.tv_nickname);
            tv_age = itemView.findViewById (R.id.tv_age);
            tv_desc = itemView.findViewById (R.id.tv_desc);
            tv_contact_name = itemView.findViewById (R.id.tv_contact_name);
            tv_contact_phone = itemView.findViewById (R.id.tv_contact_phone);
            ll_contact_info=itemView.findViewById (R.id.ll_contact_info);
        }
    }

    public interface OnClickListener {
        void OnClick(int position);
    }
}
