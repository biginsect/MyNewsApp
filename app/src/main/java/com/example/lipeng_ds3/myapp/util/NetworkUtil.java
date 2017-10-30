package com.example.lipeng_ds3.myapp.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lipeng-ds3 on 2017/10/28.
 */

public final class NetworkUtil {
    private static final String NEWS_URL = "https://news-at.zhihu.com/api/4/news/latest";
    private static final String URL_HAS_NOT_ID = "https://news-at.zhihu.com/api/4/news/";
    private static final OkHttpClient client = new OkHttpClient();

    public static String getContentFromURL(String url)throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }else{
            return null;
        }
    }

    public static String getContentFromURLAndId(String url,int id) throws IOException{
        Request request = new Request.Builder()
                .url(url + id)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }else {
            return null;
        }
    }
}