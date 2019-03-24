package com.lt.recyclerview_album_okhttp_gson.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lt.recyclerview_album_okhttp_gson.R;
import com.lt.recyclerview_album_okhttp_gson.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_album_okhttp_gson.constant.UrlConstant;
import com.lt.recyclerview_album_okhttp_gson.gson.Gank;
import com.lt.recyclerview_album_okhttp_gson.gson.GankResults;
import com.lt.recyclerview_album_okhttp_gson.ui.adapter.AlbumAdapter;
import com.lt.recyclerview_album_okhttp_gson.util.GsonUtil;
import com.lt.recyclerview_album_okhttp_gson.util.OkHttpUtil;
import com.lt.recyclerview_album_okhttp_gson.util.ToastUtil;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class AlbumActivity extends AppCompatActivity implements
        BaseRecyclerViewAdapter.OnItemClickListener,
        BaseRecyclerViewAdapter.OnItemLongClickListener, OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private AlbumAdapter mAlbumAdapter;
    private RefreshLayout mRefreshLayout;
    private List<GankResults> mGankResultsList = new ArrayList<>();
    private int mItemTotal = 10;//每页显示的item总数
    private int mPageCount = 1;//页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        bindView();
        initData();
        initView();
        initListener();
    }

    private void bindView() {
        mRecyclerView = findViewById(R.id.rcv_album);
        mRefreshLayout = findViewById(R.id.srl_album);
    }

    private void initData() {
        sendRequest();
    }

    private void initView() {
        StaggeredGridLayoutManager layoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAlbumAdapter = new AlbumAdapter(R.layout.album_item, mGankResultsList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAlbumAdapter);
        mRefreshLayout.setRefreshHeader(new BezierCircleHeader(this));
        mRefreshLayout.setRefreshFooter(
                new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void initListener() {
        mAlbumAdapter.setItemClickListener(this);
        mAlbumAdapter.setItemLongClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(AlbumActivity.this, AlbumDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) mGankResultsList);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        mAlbumAdapter.delData(position);
        return true;//消费掉这次的Click事件，不再继续触发单击事件
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        String url = UrlConstant.URL_GANK_WELFARE + "/" + mItemTotal + "/" + mPageCount;
        OkHttpUtil.getFormRequest(url, null, new OkHttpUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                mAlbumAdapter.refreshData(responseData2List(result));
                mRefreshLayout.finishRefresh(true);
                ToastUtil.showShortCenter(AlbumActivity.this, "刷新成功");
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                mRefreshLayout.finishRefresh(false);
                ToastUtil.showShortCenter(AlbumActivity.this, "刷新失败");
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageCount++;
        String url = UrlConstant.URL_GANK_WELFARE + "/" + mItemTotal + "/" + mPageCount;
        OkHttpUtil.getFormRequest(url, null, new OkHttpUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                mAlbumAdapter.loadMoreData(responseData2List(result));
                mRefreshLayout.finishLoadMore(true);
                ToastUtil.showShortCenter(AlbumActivity.this, "加载更多成功");

            }

            @Override
            public void requestFailure(Request request, IOException e) {
                mRefreshLayout.finishLoadMore(false);
                ToastUtil.showShortCenter(AlbumActivity.this, "加载更多失败");
            }
        });
    }

    private void sendRequest() {
        String url = UrlConstant.URL_GANK_WELFARE + "/" + mItemTotal + "/" + mPageCount;
        OkHttpUtil.getFormRequest(url, null, new OkHttpUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                mAlbumAdapter.refreshData(responseData2List(result));
                ToastUtil.showShortCenter(AlbumActivity.this, "数据加载成功");
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastUtil.showShortCenter(AlbumActivity.this, "数据加载失败");
            }
        });
    }

    private List<GankResults> responseData2List(String result) {
        Gank gank = GsonUtil.jsonToObject(result, Gank.class);
        return gank.gankResultsList;
    }
}