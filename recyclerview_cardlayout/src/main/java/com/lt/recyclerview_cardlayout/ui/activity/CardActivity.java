package com.lt.recyclerview_cardlayout.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.lt.recyclerview_cardlayout.R;
import com.lt.recyclerview_cardlayout.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class CardActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardAdapter mCardAdapter;
    private Toast mToast;
    private List<Integer> mIntegerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initData();
        initView();
    }

    private void bindView() {
        mRecyclerView = findViewById(R.id.rcv_main);
    }

    private void initData() {
        mIntegerList.add(R.drawable.img_avatar_01);
        mIntegerList.add(R.drawable.img_avatar_02);
        mIntegerList.add(R.drawable.img_avatar_03);
        mIntegerList.add(R.drawable.img_avatar_04);
        mIntegerList.add(R.drawable.img_avatar_05);
        mIntegerList.add(R.drawable.img_avatar_06);
        mIntegerList.add(R.drawable.img_avatar_07);
    }

    @SuppressWarnings("unchecked")
    private void initView() {
        mCardAdapter = new CardAdapter(R.layout.card_item, mIntegerList);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(mCardAdapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mRecyclerView.getAdapter(), mIntegerList);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                mCardAdapter.cardViewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    mCardAdapter.dislikeImageView.setAlpha(Math.abs(ratio));
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    mCardAdapter.likeImageView.setAlpha(Math.abs(ratio));
                } else {
                    mCardAdapter.dislikeImageView.setAlpha(0f);
                    mCardAdapter.likeImageView.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer integer, int direction) {
                mCardAdapter.cardViewHolder.itemView.setAlpha(1f);
                mCardAdapter.dislikeImageView.setAlpha(0f);
                mCardAdapter.likeImageView.setAlpha(0f);
                if (mToast == null) {
                    mToast = Toast.makeText(CardActivity.this, null, Toast.LENGTH_SHORT);
                }
                mToast.setText(direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right");
                mToast.show();
            }

            @Override
            public void onSwipedClear() {
                if (mToast == null) {
                    mToast = Toast.makeText(CardActivity.this, null, Toast.LENGTH_SHORT);
                }
                mToast.setText("data clear");
                mToast.show();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 300L);
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(mRecyclerView, touchHelper);
        mRecyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }
}
