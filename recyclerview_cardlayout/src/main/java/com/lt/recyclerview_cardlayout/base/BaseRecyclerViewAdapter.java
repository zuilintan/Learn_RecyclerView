package com.lt.recyclerview_cardlayout.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者: LinTan
 * @日期: 2018/12/12 12:10
 * @版本: 1.2
 * @描述: //RecyclerView的封装类。注意引入依赖。
 * 源址: https://blog.csdn.net/a_zhon/article/details/66971369
 * 1.0: Initial Commit
 * 1.1: 替换数据操作的视图更改方式，由notifyDataSetChanged()改为notifyItemXXXX()，提高性能。
 * ***  注意：notifyItemXXXX()修改视图不会重新执行onBindViewHolder()，
 * ***       导致了position错乱，所以需要调用notifyItemRangeChanged()重新计算position。
 * 1.2: 为数据操作添加全局Toast，解决Toast延时显示问题
 *
 * <p>
 * implementation 'com.android.support:recyclerview-v7:28.0.0'
 */

public abstract class BaseRecyclerViewAdapter<DS, VH extends ViewHolder> extends Adapter<VH>
        implements OnClickListener, OnLongClickListener {//DS:DataSource，VH:ViewHolder
    private final int VIEW_TYPE_ITEM = 1008601;
    private final int VIEW_TYPE_HEAD = 1008602;
    private final int VIEW_TYPE_FOOT = 1008603;
    private int mItemViewId;
    private int mHeadViewId = -1;
    private int mFootViewId = -1;
    private List<DS> mDataSourceList;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    private Context mContext;
    private Toast mToast;

    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId, List<DS> DataSourceList) {
        if (layoutResId == 0) {
            throw new NullPointerException("请设置item资源id");
        } else {
            mItemViewId = layoutResId;
        }
        if (DataSourceList == null) {
            mDataSourceList = new ArrayList<>();
        } else {
            mDataSourceList = DataSourceList;
        }
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(mContext).inflate(mItemViewId, viewGroup, false);
                break;
            case VIEW_TYPE_HEAD:
                view = LayoutInflater.from(mContext).inflate(mHeadViewId, viewGroup, false);
                break;
            case VIEW_TYPE_FOOT:
                view = LayoutInflater.from(mContext).inflate(mFootViewId, viewGroup, false);
                break;
        }
        assert view != null;//如果view==null，则程序停止运行
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return (VH) new BaseViewHolder(view);
    }//创建视图管理器

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_ITEM:
                if (mHeadViewId == -1) {
                    viewHolder.itemView.setTag(position);
                    bindDataToView(mDataSourceList.get(position), viewHolder, position);
                }//无HeadView时的处理
                else {
                    viewHolder.itemView.setTag(position - 1);
                    bindDataToView(mDataSourceList.get(position - 1), viewHolder, position - 1);
                }//有HeadView时的处理
                break;
            case VIEW_TYPE_HEAD:
                break;
            case VIEW_TYPE_FOOT:
                break;
        }
    }//绑定数据到视图


    @Override
    public int getItemCount() {
        if (mHeadViewId != -1 && mFootViewId != -1) {
            return mDataSourceList.size() + 2;
        }//若同时添加HeadView与FootView，数据源大小+2
        if (mHeadViewId != -1) {
            return mDataSourceList.size() + 1;
        }//若仅添加HeadView，数据源大小+1
        if (mFootViewId != -1) {
            return mDataSourceList.size() + 1;
        }//若仅添加FootView，数据源大小+1
        return mDataSourceList.size();
    }//获取数据源的大小

    @Override
    public int getItemViewType(int position) {
        int type = VIEW_TYPE_ITEM;
        if (position == 0 && mHeadViewId != -1) {
            type = VIEW_TYPE_HEAD;
        }//判断是否添加了HeadView
        if (position == getItemCount() - 1 && mFootViewId != -1) {
            type = VIEW_TYPE_FOOT;
        }//判断是否添加了FootView
        return type;
    }

    public void addHeadView(@LayoutRes int layoutResId) {
        mHeadViewId = layoutResId;
    }//添加HeadView

    public void addFootView(@LayoutRes int layoutResId) {
        mFootViewId = layoutResId;
    }//添加FootView

    public void addData(DS dataSource) {
        if (dataSource == null) {
            showToast("数据添加失败");
            return;
        }
        int oldDataSourceListSize = mDataSourceList.size();
        mDataSourceList.add(dataSource);
        notifyItemInserted(oldDataSourceListSize);
        notifyItemRangeChanged(oldDataSourceListSize, 0);
        showToast("数据已添加");
    }//添加数据

    public void delData(int position) {
        int oldDataSourceListSize = mDataSourceList.size();
        mDataSourceList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, oldDataSourceListSize - position);
        showToast("position:" + position + "数据已删除");
    }//删除数据

    public void clearData() {
        int oldDataSourceListSize = mDataSourceList.size();
        mDataSourceList.clear();
        notifyItemRangeRemoved(0, oldDataSourceListSize);
        showToast("数据已清空");
    }//清空数据

    public void refreshData(List<DS> dataSourceList) {
        if (dataSourceList == null) {
            showToast("暂无新数据");
            return;
        }
        int oldDataSourceListSize = mDataSourceList.size();
        int newDataSourceListSize = dataSourceList.size();
        mDataSourceList.clear();
        notifyItemRangeRemoved(0, oldDataSourceListSize);
        mDataSourceList.addAll(dataSourceList);
        notifyItemRangeInserted(0, newDataSourceListSize);
        showToast("数据已刷新");
    }//刷新数据

    public void loadMoreData(List<DS> dataSourceList) {
        if (dataSourceList == null) {
            showToast("暂无新数据");
            return;
        }
        int oldDataSourceListSize = mDataSourceList.size();
        int newDataSourceListSize = dataSourceList.size();
        mDataSourceList.addAll(dataSourceList);
        notifyItemRangeInserted(oldDataSourceListSize, newDataSourceListSize);
        showToast("更多数据已加载");
    }//加载更多数据

    @SuppressLint("ShowToast")
    private void showToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, null, Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
        mToast.show();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }//item点击监听器

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }//item长点击监听器

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }//点击回调

    @Override
    public boolean onLongClick(View view) {
        boolean b = false;
        if (mItemLongClickListener != null) {
            b = mItemLongClickListener.onItemLongClick(view, (Integer) view.getTag());
        }
        return b;
    }//长点击回调

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }//item点击监听器的接口

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }//item长点击监听器的接口

    protected abstract void bindDataToView(DS dataSource, VH viewHolder, int position);
}