package com.lt.recyclerview_encap.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.lt.recyclerview_encap.R;
import com.lt.recyclerview_encap.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_encap.base.BaseViewHolder;
import com.lt.recyclerview_encap.bean.TestBean;

import java.util.List;

public class TestAdapter extends BaseRecyclerViewAdapter<TestBean, BaseViewHolder> {

    public TestAdapter(int layoutResId, List<TestBean> DataSourceList) {
        super(layoutResId, DataSourceList);
    }

    @Override
    protected void bindDataToView(TestBean dataSource, BaseViewHolder viewHolder, int position) {
        ImageView imageView = viewHolder.findViewById(R.id.iv_test_image);
        TextView textView = viewHolder.findViewById(R.id.tv_test_title);
        Drawable image = dataSource.getImage();
        String title = dataSource.getTitle();
        imageView.setImageDrawable(image);
        textView.setText(title);
    }
}