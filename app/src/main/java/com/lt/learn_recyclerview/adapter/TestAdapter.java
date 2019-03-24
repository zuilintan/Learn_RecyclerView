package com.lt.learn_recyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lt.learn_recyclerview.R;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private List<String> mStringList;

    public TestAdapter(List<String> stringList) {
        mStringList = stringList;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item, viewGroup, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder testViewHolder, int i) {
        testViewHolder.mImageView.setImageResource(R.drawable.ic_windows);
        testViewHolder.mTextView.setText(mStringList.get(i));
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_test_image);
            mTextView = itemView.findViewById(R.id.tv_test_title);
        }
    }

    public void addItem(int position, String element) {
        mStringList.add(position, element);
        notifyItemInserted(position);
    }

    public void delItem(int position) {
        mStringList.remove(position);
        notifyItemRemoved(position);
    }
}
