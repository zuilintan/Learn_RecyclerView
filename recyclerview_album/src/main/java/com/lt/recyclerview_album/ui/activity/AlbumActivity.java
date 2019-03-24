package com.lt.recyclerview_album.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.lt.recyclerview_album.R;
import com.lt.recyclerview_album.adapter.AlbumAdapter;
import com.lt.recyclerview_album.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_album.bean.AlbumBean;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    public static List<AlbumBean> mAlbumBeanList=new ArrayList<>();
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private AlbumAdapter mAlbumAdapter;
    private int[] mImages = new int[]{
            R.drawable.img_avatar_01,
            R.drawable.img_avatar_02,
            R.drawable.img_avatar_03,
            R.drawable.img_avatar_04,
            R.drawable.img_avatar_05,
            R.drawable.img_avatar_06,
            R.drawable.img_avatar_07,
            R.drawable.img_avatar_08,
            R.drawable.img_avatar_09,
            R.drawable.img_avatar_10,
            R.drawable.img_avatar_11,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        bindView();
        for (int i = 0; i < 10; i++) {
            initData();
        }
        initView();
        initListener();
    }

    private void bindView() {
        mToolbar = findViewById(R.id.tb_top);
        mRecyclerView = findViewById(R.id.rcv_album);
    }

    private void initData() {
        for (int i = 0; i < 11; i++) {
            AlbumBean albumBean = new AlbumBean();
            albumBean.setImage(getDrawable(mImages[i]));
            albumBean.setTitle(getResources().getResourceEntryName(mImages[i]));
            mAlbumBeanList.add(albumBean);
        }
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        StatusBarUtil.setTransparent(this);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mAlbumAdapter = new AlbumAdapter(R.layout.album_item, mAlbumBeanList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAlbumAdapter);
    }

    private void initListener() {
        mAlbumAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Intent intent = new Intent();
                intent.setClass(AlbumActivity.this, AlbumDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolBar_menu_search:
                return true;
            case R.id.toolBar_menu_explore:
                return true;
            case R.id.toolBar_menu_setting:
                return true;
            case R.id.toolBar_menu_about:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
