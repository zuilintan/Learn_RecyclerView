package com.lt.recyclerview_encap.bean;

import android.graphics.drawable.Drawable;

public class TestBean {
    private Drawable mImage;
    private String mTitle;

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
