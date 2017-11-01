package com.lipeng.newapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lipeng.newapp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/30.
 * {@link User}对应的数据库操作类，主要方法是{@link #getUser()}用于获取用户的内容-账号和密码
 */

public class UserDatabase {
    private static final String DB_NAME = "user";
    private static final int VERSION = 1;
    private static UserDatabase userDatabase;
    private SQLiteDatabase database;

    private UserDatabase(Context context){
        MyDatabaseHelper helper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
        database = helper.getWritableDatabase();
    }

    public synchronized static UserDatabase getInstance(Context context){//单例模式
        if (userDatabase == null){
            userDatabase = new UserDatabase(context);
        }
        return userDatabase;
    }

    public List<User> getUser(){//在数据库中获取账号密码
        List<User> userList = new ArrayList<>();
        Cursor cursor = database.query("user", null, null, null ,null ,null, null);
        if (cursor.moveToFirst()){
            do {
                User user =new User();
                user.setAccount(cursor.getString(cursor.getColumnIndex("account")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userList.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return userList;
    }
}