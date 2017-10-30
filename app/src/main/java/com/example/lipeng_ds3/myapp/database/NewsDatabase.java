package com.example.lipeng_ds3.myapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.lipeng_ds3.myapp.model.News;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 */

public final class NewsDatabase {
    public static final String DB_NAME = "MyNews";
    public static final int DB_VERSION = 1;
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
            values.put("",news.getNewsContent());
            database.insertWithOnConflict("news", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
}
