package com.lt.recyclerview_album_okhttp_gson.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @作者: LinTan
 * @日期: 2018/12/25 17:15
 * @版本: 1.1
 * @描述: //OkHttp的工具类。注意引入依赖。
 * 源址: 《第一行代码(第二版)》中 9.5 网络编程的最佳实践 内的Example
 * 1.0: Initial Commit
 * 1.1: 新增请求体为json的Post请求
 * <p>
 * implementation 'com.squareup.okhttp3:okhttp:3.12.1'
 */

public class OkHttpUtil1 {

    /**
     * OkHttp的Get请求
     */
    public static void sendGetRequest(String adress, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(adress)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * OkHttp的Post请求，请求体为Json字符串
     */
    public static void sendPostRequest(String adress, String json, okhttp3.Callback callback) {
        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(jsonType, json);
        Request request = new Request.Builder()
                .url(adress)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
