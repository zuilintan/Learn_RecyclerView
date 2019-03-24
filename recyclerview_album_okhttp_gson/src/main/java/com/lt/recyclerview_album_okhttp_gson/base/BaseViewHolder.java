package com.lt.recyclerview_album_okhttp_gson.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @作者: LinTan
 * @日期: 2018/12/12 12:10
 * @版本: 1.0
 * @描述: //RecyclerView的封装类。注意引入依赖。
 * 源址: https://blog.csdn.net/a_zhon/article/details/66971369
 * 1.0: Initial Commit
 * <p>
 * implementation 'com.android.support:recyclerview-v7:28.0.0'
 */

public class BaseViewHolder extends ViewHolder {
    private SparseArray<View> mViewSparseArray;//Key为View的Id，Value为View对象

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViewSparseArray = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int viewId) {
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }//findViewById，并存储到SparseArray中

    public ImageView setImageResource(int viewId, @DrawableRes int imageResId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(imageResId);
        return view;
    }//设置图片

    public TextView setText(int viewId, CharSequence s) {
        TextView view = findViewById(viewId);
        view.setText(s);
        return view;
    }//设置文本
}