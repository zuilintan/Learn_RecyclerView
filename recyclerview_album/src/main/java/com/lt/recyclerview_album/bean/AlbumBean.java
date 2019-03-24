package com.lt.recyclerview_album.bean;

import android.graphics.drawable.Drawable;

public class AlbumBean {
    private Drawable Image;
    private String Title;

    public Drawable getImage() {
        return Image;
    }

    public void setImage(Drawable image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
