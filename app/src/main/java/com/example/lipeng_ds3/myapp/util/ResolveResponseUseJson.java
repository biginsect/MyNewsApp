package com.example.lipeng_ds3.myapp.util;

import android.util.Log;

import com.example.lipeng_ds3.myapp.database.NewsDatabase;
import com.example.lipeng_ds3.myapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 */

public final class ResolveResponseUseJson {
    private final static String TAG = "ResolveResponseUseJson";
    //处理请求返回的string类型数据
    public synchronized static void handleResponse(NewsDatabase dataBase, String response){
        if (response == null){
            Log.d(TAG, " handleResponse: response is null");
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
            String[] stories = new String[jsonArrayStories.length()];
            for (int i = 0; i < stories.length; i++){
                stories[i] = jsonArrayStories.getString(i);
            }
            handleJSONArray(dataBase, stories);

            JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
            String[] topStories = new String[jsonArrayTopStories.length()];
            for (int i = 0; i < topStories.length; i++){
                topStories[i] = jsonArrayTopStories.getString(i);
            }
            handleJSONArrayTopStories(dataBase, topStories);
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
                String newsContent = NetworkUtil.getContentFromURLAndId(NetworkUtil.URL_HAS_NOT_ID, Integer.parseInt(newsId));
                news.setNewsImageUrl(newsImageUrl);
                news.setNewsId(Integer.parseInt(newsId));
                news.setNewsTitle(newsTitle);
                news.setNewsContent(newsContent);
//                Log.d(TAG, "newsImageUrl "+ newsImageUrl + "\n" + "newsId" + newsId);
                dataBase.saveNews(news);
            }catch (JSONException e){
                e.printStackTrace();
            }
            i++;
        }
    }

    public synchronized static void handleJSONArrayTopStories(NewsDatabase dataBase, String[] arr){
        int i = 0;
        while (i != arr.length){
            try{
                News news = new News();
                JSONObject jsonObject = new JSONObject(arr[i]);
                String newsImageUrl = jsonObject.getString("image");
                String newsId = jsonObject.getString("id");
                String newsTitle = jsonObject.getString("title");
                String newsContent = NetworkUtil.getContentFromURLAndId(NetworkUtil.URL_HAS_NOT_ID, Integer.parseInt(newsId));
                news.setNewsImageUrl(newsImageUrl);
                news.setNewsId(Integer.parseInt(newsId));
                news.setNewsTitle(newsTitle);
                news.setNewsContent(newsContent);
//                Log.d(TAG, "newsImageUrl "+ newsImageUrl + "\n" + "newsId" + newsId);
                dataBase.saveNews(news);
            }catch (JSONException e){
                e.printStackTrace();
            }
            i++;
        }
    }

    //处理请求返回的内容，有body和css两种数据
    public synchronized static String[] handleWebViewResponse(String response){
        String[] code = new String[2];
        try{
            JSONObject jsonObject = new JSONObject(response);
            code[0] = jsonObject.getString("body");
            code[1] = jsonObject.getString("css");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return code;
    }

}
