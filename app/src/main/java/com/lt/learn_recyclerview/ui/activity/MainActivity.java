package com.lt.learn_recyclerview.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.lt.learn_recyclerview.R;
import com.lt.learn_recyclerview.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initView();
    }

    private void bindView() {
        mRecyclerView = findViewById(R.id.rcv_show_content);
    }

    private List<String> initData() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            stringList.add(String.valueOf(i));
        }
        return stringList;
    }

    private void initView() {
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        mTestAdapter = new TestAdapter(initData());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(mTestAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionBar_menu_addItem:
                mTestAdapter.addItem(0, "New");
                mRecyclerView.scrollToPosition(0);
                break;
            case R.id.actionBar_menu_delItem:
                mTestAdapter.delItem(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
