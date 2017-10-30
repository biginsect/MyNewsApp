package com.example.lipeng_ds3.myapp.util;

import android.content.Context;
import android.util.Log;

import com.example.lipeng_ds3.myapp.database.NewsDatabase;
import com.example.lipeng_ds3.myapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 */

public final class ResolveContentUseJson {
    private final static String TAG = "ResolveContentUseJson";
    //处理请求返回的string类型数据
    public synchronized static void handleResponse(NewsDatabase dataBase, String response){
        if (response == null){
            Log.d(TAG, " handleResponse: response is null");
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("stories");
            String[] stories = new String[jsonArray.length()];
            for (int i = 0; i < stories.length; i++){
                stories[i] = jsonArray.getString(i);
            }
            handleJSONArray(dataBase, stories);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //使用JSON解析数据
    public synchronized static void handleJSONArray(NewsDatabase dataBase, String[] arr){
        int i = 0;
        while (i != arr.length){
            try{
                News news = new News();
                JSONObject jsonObject = new JSONObject(arr[i]);
                JSONArray jsonArray = jsonObject.getJSONArray("images");
                String newsImageUrl = jsonArray.get(0).toString();
                String newsId = jsonObject.getString("id");
                String newsTitle = jsonObject.getString("title");
                news.setNewsImageUrl(newsImageUrl);
                news.setNewsId(Integer.parseInt(newsId));
                news.setNewsTitle(newsTitle);
//                Log.d(TAG, "newsImageUrl "+ newsImageUrl + "\n" + "newsId" + newsId);
                dataBase.saveNews(news);
            }catch (JSONException e){
                e.printStackTrace();
            }
            i++;
        }
    }

    public synchronized static void handleResponseForUpdateDB(NewsDatabase database, int id, String response){
        if (response == null){
            Log.d(TAG, "handleResponseForUpdateDB : response is null");
            return;
        }
        try{
            JSONObject jsonObject = new JSONObject(response);
            String content = jsonObject.getString("body");
//            Log.d(TAG, content.substring(0 , 100));
            database.updateNewsContent(id, content);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
