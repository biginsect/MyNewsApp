package com.example.lipeng_ds3.myapp.util;

import com.example.lipeng_ds3.myapp.database.NewsDatabase;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lipeng-ds3 on 2017/10/28.
 */

public final class NetworkUtil {
    public static final String NEWS_URL = "https://news-at.zhihu.com/api/4/news/latest";
    public static final String URL_HAS_NOT_ID = "https://news-at.zhihu.com/api/4/news/";
    private static final OkHttpClient client = new OkHttpClient();

    public static void getContentFromURL(final NewsDatabase database, String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.code());
                ResolveContentUseJson.handleResponse(database, response.body().string());
            }
        });

    }

    //根据news id去请求数据
    public static void getContentFromURLAndId(final NewsDatabase database,String url,final int id){
        Request request = new Request.Builder()
                .url(url + id)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功  更新数据库
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.code());
                ResolveContentUseJson.handleResponseForUpdateDB(database, id, response.body().string());
            }
        });
    }
}