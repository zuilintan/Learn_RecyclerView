package com.lt.recyclerview_album_kotlin.adapter

import com.lt.recyclerview_album_kotlin.base.BaseRecyclerViewAdapter
import com.lt.recyclerview_album_kotlin.base.BaseViewHolder
import com.lt.recyclerview_album_kotlin.bean.AlbumBean
import com.lt.recyclerview_album_kotlin.util.GlideUtil
import kotlinx.android.synthetic.main.album_item.view.*

class AlbumAdapter(layoutResId: Int, DataSourceList: MutableList<AlbumBean>?) : BaseRecyclerViewAdapter<AlbumBean, BaseViewHolder>(layoutResId, DataSourceList) {
    override fun bindDataToView(dataSource: AlbumBean, viewHolder: BaseViewHolder, position: Int) {
        val imageView = viewHolder.itemView.iv_album_item_img
        GlideUtil.loadImage(viewHolder.itemView.context, dataSource.image, imageView)
    }//需要注释GlideUtil中centerCrop()才能正常显示瀑布流
}