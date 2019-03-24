package com.lt.recyclerview_album_okhttp_gson.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lt.recyclerview_album_okhttp_gson.R;
import com.lt.recyclerview_album_okhttp_gson.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_album_okhttp_gson.base.BaseViewHolder;
import com.lt.recyclerview_album_okhttp_gson.gson.GankResults;
import com.lt.recyclerview_album_okhttp_gson.util.GlideUtil;

import java.util.List;

public class AlbumAdapter extends BaseRecyclerViewAdapter<GankResults, BaseViewHolder> {

    public AlbumAdapter(int layoutResId, List<GankResults> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void bindDataToView(GankResults dataSource, BaseViewHolder viewHolder, int position) {
        View itemView = viewHolder.itemView;
        Context context = itemView.getContext();
        String imgUrl = dataSource.imgUrl;
        ImageView imageView = viewHolder.findViewById(R.id.iv_album_item_img);
        GlideUtil.loadImageSkipMemoryCache(context, imgUrl, imageView);
    }
}
