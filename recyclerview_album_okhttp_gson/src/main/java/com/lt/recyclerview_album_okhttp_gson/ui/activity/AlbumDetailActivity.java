package com.lt.recyclerview_album_okhttp_gson.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.lt.recyclerview_album_okhttp_gson.R;
import com.lt.recyclerview_album_okhttp_gson.ui.adapter.AlbumDetailAdapter;
import com.lt.recyclerview_album_okhttp_gson.gson.GankResults;
import com.lt.recyclerview_album_okhttp_gson.util.ScreenUtil;

import java.util.List;

public class AlbumDetailActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AlbumDetailAdapter mAlbumDetailAdapter;
    private List<GankResults> mGankResultsList;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        bindView();
        initData();
        initView();
    }

    private void bindView() {
        mRecyclerView = findViewById(R.id.rcv_album_detail);
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mGankResultsList = (List<GankResults>) bundle.getSerializable("list");
        mCurrentPosition = bundle.getInt("position");
    }

    private void initView() {
        ScreenUtil.setNoStatusBar(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAlbumDetailAdapter = new AlbumDetailAdapter(R.layout.album_detail_item, mGankResultsList);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAlbumDetailAdapter);
        mRecyclerView.scrollToPosition(mCurrentPosition);//根据bundle接收的position滑动到指定位置
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);//这两句实现了ViewPage效果
    }
}
