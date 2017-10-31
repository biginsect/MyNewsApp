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
            JSONArray jsonArrayStories = jsonObject.getJSONArray("stories");
            JSONArray jsonArrayTopStories = jsonObject.getJSONArray("top_stories");
            String[] stories = new String[jsonArrayStories.length()];
            for (int i = 0; i < stories.length; i++){
                stories[i] = jsonArrayStories.getString(i);
            }
            handleJSONArray(dataBase, stories);

            String[] topStories = new String[jsonArrayTopStories.length()];
            for (int i = 0; i < topStories.length; i++){
                topStories[i] = jsonArrayTopStories.getString(i);
            }
            handleJSONArrayTopStories(dataBase, topStories);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

/*    //合并两个JSONArray
    public static JSONArray mergeJSONArray(JSONArray array1, JSONArray array2){
        StringBuffer buffer = new StringBuffer();
        try{
            int len = array1.length();
            for (int i =0; i < len; i++){
                JSONObject object = (JSONObject) array1.get(i);
                if ( i == len - 1){
                    buffer.append(object.toString());
                }else {
                    buffer.append(object.toString()).append(",");
                }
            }
            len = array2.length();
            if (len > 0)
                buffer.append(",");
            for (int i = 0; i < len; i++){
                JSONObject object = (JSONObject) array2.get(i);
                if (i == len - 1)
                    buffer.append(object.toString());
                else
                    buffer.append(object.toString()).append(",");
            }
            buffer.insert(0, "[").append("]");
            return new JSONArray(buffer.toString());
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }*/

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

/*    public synchronized static void handleResponseForUpdateDB(NewsDatabase database, int id, String response){
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
    }*/
}
