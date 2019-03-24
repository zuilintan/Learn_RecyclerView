package com.lt.recyclerview_album_kotlin.adapter

import com.lt.recyclerview_album_kotlin.base.BaseRecyclerViewAdapter
import com.lt.recyclerview_album_kotlin.base.BaseViewHolder
import com.lt.recyclerview_album_kotlin.bean.AlbumBean
import com.lt.recyclerview_album_kotlin.util.GlideUtil
import kotlinx.android.synthetic.main.album_detail_item.view.*

class AlbumDetailAdapter(layoutResId: Int, DataSourceList: MutableList<AlbumBean>?) : BaseRecyclerViewAdapter<AlbumBean, BaseViewHolder>(layoutResId, DataSourceList) {
    override fun bindDataToView(dataSource: AlbumBean, viewHolder: BaseViewHolder, position: Int) {
        val imageView = viewHolder.itemView.iv_album_detail_item_img
        GlideUtil.loadImage(viewHolder.itemView.context, dataSource.image, imageView)
        val textView = viewHolder.itemView.tv_album_detail_item_txt
        textView.text = dataSource.title
    }
}