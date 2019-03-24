package com.lt.recyclerview_album_kotlin.base

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Toast

import java.util.ArrayList

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
 *
 *
 * implementation 'com.android.support:recyclerview-v7:28.0.0'
 */

abstract class BaseRecyclerViewAdapter<DS, VH : ViewHolder>(@LayoutRes layoutResId: Int, DataSourceList: MutableList<DS>?) : Adapter<VH>(), OnClickListener, OnLongClickListener {
    //DS:DataSource，VH:ViewHolder
    private val VIEW_TYPE_ITEM = 1008601
    private val VIEW_TYPE_HEAD = 1008602
    private val VIEW_TYPE_FOOT = 1008603
    private var mItemViewId: Int = 0
    private var mHeadViewId = -1
    private var mFootViewId = -1
    private var mDataSourceList: MutableList<DS>? = null
    private var mItemClickListener: OnItemClickListener? = null
    private var mItemLongClickListener: OnItemLongClickListener? = null
    private var mContext: Context? = null
    private var mToast: Toast? = null

    init {
        if (layoutResId == 0) {
            throw NullPointerException("请设置item资源id")
        } else {
            mItemViewId = layoutResId
        }
        if (DataSourceList == null) {
            mDataSourceList = ArrayList()
        } else {
            mDataSourceList = DataSourceList
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VH {
        mContext = viewGroup.context
        var view: View? = null
        when (viewType) {
            VIEW_TYPE_ITEM -> view = LayoutInflater.from(mContext).inflate(mItemViewId, viewGroup, false)
            VIEW_TYPE_HEAD -> view = LayoutInflater.from(mContext).inflate(mHeadViewId, viewGroup, false)
            VIEW_TYPE_FOOT -> view = LayoutInflater.from(mContext).inflate(mFootViewId, viewGroup, false)
        }
        assert(view != null)//如果view==null，则程序停止运行
        view!!.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return BaseViewHolder(view) as VH
    }//创建视图管理器

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        when (viewHolder.itemViewType) {
            VIEW_TYPE_ITEM -> if (mHeadViewId == -1) {
                viewHolder.itemView.tag = position
                bindDataToView(mDataSourceList!![position], viewHolder, position)
            }//无HeadView时的处理
            else {
                viewHolder.itemView.tag = position - 1
                bindDataToView(mDataSourceList!![position - 1], viewHolder, position - 1)
            }//有HeadView时的处理
            VIEW_TYPE_HEAD -> {
            }
            VIEW_TYPE_FOOT -> {
            }
        }
    }//绑定数据到视图


    override fun getItemCount(): Int {
        if (mHeadViewId != -1 && mFootViewId != -1) {
            return mDataSourceList!!.size + 2
        }//若同时添加HeadView与FootView，数据源大小+2
        if (mHeadViewId != -1) {
            return mDataSourceList!!.size + 1
        }//若仅添加HeadView，数据源大小+1
        return if (mFootViewId != -1) {
            mDataSourceList!!.size + 1
        } else mDataSourceList!!.size//若仅添加FootView，数据源大小+1
    }//获取数据源的大小

    override fun getItemViewType(position: Int): Int {
        var type = VIEW_TYPE_ITEM
        if (position == 0 && mHeadViewId != -1) {
            type = VIEW_TYPE_HEAD
        }//判断是否添加了HeadView
        if (position == itemCount - 1 && mFootViewId != -1) {
            type = VIEW_TYPE_FOOT
        }//判断是否添加了FootView
        return type
    }

    fun addHeadView(@LayoutRes layoutResId: Int) {
        mHeadViewId = layoutResId
    }//添加HeadView

    fun addFootView(@LayoutRes layoutResId: Int) {
        mFootViewId = layoutResId
    }//添加FootView

    fun addData(dataSource: DS?) {
        if (dataSource == null) {
            showToast("数据添加失败")
            return
        }
        val oldDataSourceListSize = mDataSourceList!!.size
        mDataSourceList!!.add(dataSource)
        notifyItemInserted(oldDataSourceListSize)
        notifyItemRangeChanged(oldDataSourceListSize, 0)
        showToast("数据已添加")
    }//添加数据

    fun delData(position: Int) {
        val oldDataSourceListSize = mDataSourceList!!.size
        mDataSourceList!!.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, oldDataSourceListSize - position)
        showToast("position:" + position + "数据已删除")
    }//删除数据

    fun clearData() {
        val oldDataSourceListSize = mDataSourceList!!.size
        mDataSourceList!!.clear()
        notifyItemRangeRemoved(0, oldDataSourceListSize)
        showToast("数据已清空")
    }//清空数据

    fun refreshData(dataSourceList: List<DS>?) {
        if (dataSourceList == null) {
            showToast("暂无新数据")
            return
        }
        val oldDataSourceListSize = mDataSourceList!!.size
        val newDataSourceListSize = dataSourceList.size
        mDataSourceList!!.clear()
        notifyItemRangeRemoved(0, oldDataSourceListSize)
        mDataSourceList!!.addAll(dataSourceList)
        notifyItemRangeInserted(0, newDataSourceListSize)
        showToast("数据已刷新")
    }//刷新数据

    fun loadMoreData(dataSourceList: List<DS>?) {
        if (dataSourceList == null) {
            showToast("暂无新数据")
            return
        }
        val oldDataSourceListSize = mDataSourceList!!.size
        val newDataSourceListSize = dataSourceList.size
        mDataSourceList!!.addAll(dataSourceList)
        notifyItemRangeInserted(oldDataSourceListSize, newDataSourceListSize)
        showToast("更多数据已加载")
    }//加载更多数据

    @SuppressLint("ShowToast")
    private fun showToast(message: String) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, null, Toast.LENGTH_SHORT)
        }
        mToast!!.setText(message)
        mToast!!.show()
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        mItemClickListener = itemClickListener
    }//item点击监听器

    fun setItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        mItemLongClickListener = itemLongClickListener
    }//item长点击监听器

    override fun onClick(view: View) {
        if (mItemClickListener != null) {
            mItemClickListener!!.onItemClick(view, view.tag as Int)
        }
    }//点击回调

    override fun onLongClick(view: View): Boolean {
        var b = false
        if (mItemLongClickListener != null) {
            b = mItemLongClickListener!!.onItemLongClick(view, view.tag as Int)
        }
        return b
    }//长点击回调

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }//item点击监听器的接口

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int): Boolean
    }//item长点击监听器的接口

    protected abstract fun bindDataToView(dataSource: DS, viewHolder: VH, position: Int)
}