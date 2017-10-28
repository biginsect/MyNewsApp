package com.example.lipeng_ds3.myapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lipeng-ds3 on 2017/10/28.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_USER = "create table user(" +
            "account text primary key," +
            "password text)";
    private static final String DB_NAME = "user";
    private static final int VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//第一次调用getWritableDatabase 或者getReadableDatabase执行该方法
        db.execSQL(CREATE_USER);
        db.execSQL("insert into user (account, password) values(?, ?)",new String[]{"lipeng-ds3", "123"});
        db.execSQL("insert into user (account, password) values(?, ?)",new String[]{"lipeng", "123"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
