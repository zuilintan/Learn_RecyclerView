package com.lt.recyclerview_encap.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lt.recyclerview_encap.R;
import com.lt.recyclerview_encap.adapter.TestAdapter;
import com.lt.recyclerview_encap.base.BaseRecyclerViewAdapter;
import com.lt.recyclerview_encap.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;
    private TestBean mTestBean;
    //    private List<TestBean> mTestBeanList = new ArrayList<>(Collections.nCopies(20, mTestBean));//等价于执行20次add(testBean)
    private List<TestBean> mTestBeanList = new ArrayList<>();
    private List<TestBean> mTestBeanListNew = new ArrayList<>();
    private int[] mImages = new int[]{
            R.drawable.ic_image01,
            R.drawable.ic_image02,
            R.drawable.ic_image03,
            R.drawable.ic_image04
    };
    private String[] mTitles = new String[]{
            "A",
            "B",
            "C",
            "D"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initData();
        initView();
        initListener();
    }

    private void bindView() {
        mRecyclerView = findViewById(R.id.rcv_show_content);
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            TestBean testBean = new TestBean();
            testBean.setImage(getDrawable(mImages[i]));
            testBean.setTitle(mTitles[i]);
            mTestBeanList.add(testBean);
        }
        mTestBean = new TestBean();
        mTestBean.setImage(getDrawable(R.drawable.ic_windows));
        mTestBean.setTitle("New");
        mTestBeanListNew.addAll(mTestBeanList);
        mTestBeanListNew.add(mTestBean);
    }

    private void initView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        mTestAdapter = new TestAdapter(R.layout.test_item, mTestBeanList);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(itemDecoration);
//        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(mTestAdapter);
    }

    private void initListener() {
        mTestAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mTestAdapter.setItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                mTestAdapter.delData(position);
                return true;
            }
        });
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
                mTestAdapter.addData(mTestBean);
                mRecyclerView.smoothScrollToPosition(mTestBeanList.size());
                break;
            case R.id.actionBar_menu_delItem:
                mTestAdapter.clearData();
                Toast.makeText(this, "长按item删除数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionBar_menu_refreshItem:
                mTestAdapter.refreshData(mTestBeanListNew);
                break;
            case R.id.actionBar_menu_LoadMoreItem:
                mTestAdapter.loadMoreData(mTestBeanListNew);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
