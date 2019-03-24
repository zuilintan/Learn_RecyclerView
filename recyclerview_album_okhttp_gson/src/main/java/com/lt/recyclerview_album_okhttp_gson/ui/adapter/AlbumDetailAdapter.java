package com.lt.recyclerview_album_okhttp_gson.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.lt.recyclerview_album_okhttp_gson.R;
import com.lt.recyclerview_album_okhttp_gson.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_album_okhttp_gson.base.BaseViewHolder;
import com.lt.recyclerview_album_okhttp_gson.gson.GankResults;
import com.lt.recyclerview_album_okhttp_gson.util.GlideUtil;

import java.util.List;

public class AlbumDetailAdapter extends BaseRecyclerViewAdapter<GankResults, BaseViewHolder> {

    public AlbumDetailAdapter(int layoutResId, List<GankResults> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void bindDataToView(GankResults dataSource, BaseViewHolder viewHolder, int position) {
        Context context = viewHolder.itemView.getContext();
        String imgUrl = dataSource.imgUrl;
        ImageView imageView = viewHolder.findViewById(R.id.iv_album_detail_item_img);
        GlideUtil.loadImageSkipMemoryCache(context, imgUrl, imageView);
    }
}
