package com.lt.recyclerview_album_okhttp_gson.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GankResults implements Serializable {
    /**
     * _id : 5c2dabdb9d21226e068debf9
     * createdAt : 2019-01-03T06:29:47.895Z
     * desc : 2019-01-03
     * publishedAt : 2019-01-03T00:00:00.0Z
     * source : web
     * type : 福利
     * url : https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg
     * used : true
     * who : lijinshanmx
     */

    @SerializedName("_id")
    public String id;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("desc")
    public String date;
    @SerializedName("publishedAt")
    public String publishedAt;
    @SerializedName("source")
    public String source;
    @SerializedName("type")
    public String type;
    @SerializedName("url")
    public String imgUrl;
    @SerializedName("used")
    public boolean used;
    @SerializedName("who")
    public String who;
}
