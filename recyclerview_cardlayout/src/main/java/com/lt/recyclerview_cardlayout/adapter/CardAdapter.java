package com.lt.recyclerview_cardlayout.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.lt.recyclerview_cardlayout.R;
import com.lt.recyclerview_cardlayout.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_cardlayout.base.BaseViewHolder;

import java.util.List;

public class CardAdapter extends BaseRecyclerViewAdapter<Integer, BaseViewHolder> {
    public BaseViewHolder cardViewHolder;
    public ImageView avatarImageView;
    public ImageView likeImageView;
    public ImageView dislikeImageView;


    public CardAdapter(int layoutResId, List<Integer> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void bindDataToView(Integer dataSource, BaseViewHolder viewHolder, int position) {
        cardViewHolder = viewHolder;
        avatarImageView = viewHolder.itemView.findViewById(R.id.iv_avatar);
        likeImageView = viewHolder.itemView.findViewById(R.id.iv_like);
        dislikeImageView = viewHolder.itemView.findViewById(R.id.iv_dislike);
        avatarImageView.setImageResource(dataSource);
        if (dataSource == R.drawable.img_avatar_07) {
            TextView textView = viewHolder.itemView.findViewById(R.id.tv_name);
            textView.setText("傻猫");
        }
    }
}
