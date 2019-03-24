package com.lt.recyclerview_album.adapter;

import android.widget.ImageView;

import com.lt.recyclerview_album.R;
import com.lt.recyclerview_album.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_album.base.BaseViewHolder;
import com.lt.recyclerview_album.bean.AlbumBean;
import com.lt.recyclerview_album.util.GlideUtil;

import java.util.List;

public class AlbumAdapter extends BaseRecyclerViewAdapter<AlbumBean, BaseViewHolder> {

    public AlbumAdapter(int layoutResId, List<AlbumBean> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void bindDataToView(final AlbumBean dataSource, BaseViewHolder viewHolder, int position) {
        ImageView imageView = viewHolder.findViewById(R.id.iv_album_item_img);
        GlideUtil.loadImage(viewHolder.itemView.getContext(), dataSource.getImage(), imageView);
    }//需要注释GlideUtil中centerCrop()才能正常显示瀑布流
}
