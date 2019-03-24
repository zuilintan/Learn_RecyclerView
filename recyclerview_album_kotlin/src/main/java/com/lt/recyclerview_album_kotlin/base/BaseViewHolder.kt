package com.lt.recyclerview_album_kotlin.base

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * @作者: LinTan
 * @日期: 2018/12/12 12:10
 * @版本: 1.0
 * @描述: //RecyclerView的封装类。注意引入依赖。
 * 源址: https://blog.csdn.net/a_zhon/article/details/66971369
 * 1.0: Initial Commit
 *
 *
 * implementation 'com.android.support:recyclerview-v7:28.0.0'
 */

class BaseViewHolder(itemView: View) : ViewHolder(itemView) {
    private val mViewSparseArray: SparseArray<View>//Key为View的Id，Value为View对象

    init {
        mViewSparseArray = SparseArray()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> findViewById(viewId: Int): T {
        var view: View? = mViewSparseArray.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViewSparseArray.put(viewId, view)
        }
        return (view as T?)!!
    }//findViewById，并存储到SparseArray中

    fun setImageResource(viewId: Int, @DrawableRes imageResId: Int): ImageView {
        val view = findViewById<ImageView>(viewId)
        view.setImageResource(imageResId)
        return view
    }//设置图片

    fun setText(viewId: Int, s: CharSequence): TextView {
        val view = findViewById<TextView>(viewId)
        view.text = s
        return view
    }//设置文本
}