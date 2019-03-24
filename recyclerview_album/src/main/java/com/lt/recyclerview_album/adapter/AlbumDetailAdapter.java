package com.lt.recyclerview_album.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.lt.recyclerview_album.R;
import com.lt.recyclerview_album.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_album.base.BaseViewHolder;
import com.lt.recyclerview_album.bean.AlbumBean;
import com.lt.recyclerview_album.util.GlideUtil;

import java.util.List;

public class AlbumDetailAdapter extends BaseRecyclerViewAdapter<AlbumBean, BaseViewHolder> {

    public AlbumDetailAdapter(int layoutResId, List<AlbumBean> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void bindDataToView(AlbumBean dataSource, BaseViewHolder viewHolder, int position) {
        ImageView imageView = viewHolder.findViewById(R.id.iv_album_detail_item_img);
        GlideUtil.loadImage(viewHolder.itemView.getContext(), dataSource.getImage(), imageView);
        TextView textView = viewHolder.findViewById(R.id.tv_album_detail_item_txt);
        textView.setText(dataSource.getTitle());
    }
}
