package com.example.lipeng_ds3.myapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lipeng_ds3.myapp.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 */

public final class NewsDatabase {
    private static final String TAG = "NewsDatabase";
    public static volatile boolean flag = false;

    private static final String DB_NAME = "MyNews";
    private static final int DB_VERSION = 1;
    private static NewsDatabase newsDataBase ;
    private SQLiteDatabase database;

    private NewsDatabase(Context context){
        MyDatabaseHelper helper = new MyDatabaseHelper(context, DB_NAME, null, DB_VERSION);
        database = helper.getWritableDatabase();
    }

    public synchronized static NewsDatabase getInstance(Context context){
        if (newsDataBase == null){
            newsDataBase = new NewsDatabase(context);
        }
        return newsDataBase;
    }

    public void saveNews(News news){
        if (news != null){
            ContentValues values = new ContentValues();
            values.put("id", news.getNewsId());
            values.put("imageUrl",news.getNewsImageUrl());
            values.put("title",news.getNewsTitle());
            values.put("content",news.getNewsContent());
            database.insertWithOnConflict("news", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public List<News> loadNews(){
        List<News> newsList = new ArrayList<>();
        Cursor cursor = database.query("news", null, null, null, null, null, null);
//        String start = "</span>\n</div>\n\n<div class=\"content\">\n<p>";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            News news = new News();
            news.setNewsId(cursor.getInt(cursor.getColumnIndex("id")));
            news.setNewsImageUrl(cursor.getString(cursor.getColumnIndex("imageUrl")));
            news.setNewsTitle(cursor.getString(cursor.getColumnIndex("title")));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            if (content != null){
                content = content.replaceAll("[^\u4E00-\u9FA5]","");
                news.setNewsContent(content);
            }else {
                flag = false;
            }
            Log.d(TAG, news.getNewsImageUrl());
            newsList.add(news);
        }
        if (cursor != null)
                cursor.close();
        return newsList;
    }

}
