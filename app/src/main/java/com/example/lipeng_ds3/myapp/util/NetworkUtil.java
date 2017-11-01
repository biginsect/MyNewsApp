package com.example.lipeng_ds3.myapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.lipeng_ds3.myapp.database.NewsDatabase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();

    public static boolean getContentFromURL(final NewsDatabase database, String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response.code());
                ResolveResponseUseJson.handleResponse(database, response.body().string());
            }
        });
        return true;

    }

    //通过url加载数据，并将数据存储到SharedPreferences中
    public static void loadWebViewFromURL(final Context context,String url){
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
                if (response.isSuccessful()){
                    String[] code = ResolveResponseUseJson.handleWebViewResponse(response.body().string());
                    saveWebViewCode(context, code);
                }
            }
        });
    }

    //根据news id去请求content
    public static String getContentFromURLAndId(String url,final int id){
        Request request = new Request.Builder()
                .url(url + id)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static void saveWebViewCode(Context context, String[] code){
        if (code.length > 1){
            SharedPreferences htmlPreferences = context.getSharedPreferences("html", Context.MODE_PRIVATE);
            SharedPreferences.Editor htmlEditor = htmlPreferences.edit();
            //富文本设置
//            String richCodeSetting = "<head><style>img{max-width:100% !important;} table{max-width:100% !important;}</style></head>";
            htmlEditor.putString("html",code[0] );
            htmlEditor.apply();

            SharedPreferences cssPreferences = context.getSharedPreferences("css.css", Context.MODE_PRIVATE);
            SharedPreferences.Editor cssEditor = cssPreferences.edit();
            cssEditor.putString("css", code[1]);
            cssEditor.apply();
        }
    }

    //检查网络状态
    public static boolean isNetworkAvailable(Activity activity){
        Context context = activity.getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info.getState() == NetworkInfo.State.CONNECTED)
                return true;
        }
        return false;
    }
}