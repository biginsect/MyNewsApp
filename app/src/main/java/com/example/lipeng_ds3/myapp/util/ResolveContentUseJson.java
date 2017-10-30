package com.example.lipeng_ds3.myapp.util;

import com.example.lipeng_ds3.myapp.database.NewsDatabase;
import com.example.lipeng_ds3.myapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 */

public final class ResolveContentUseJson {
    //处理请求返回的string类型数据
    public synchronized static void handleResponse(NewsDatabase dataBase, String response){
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
                dataBase.saveNews(news);
            }catch (JSONException e){
                e.printStackTrace();
            }
            i++;
        }
    }
}
