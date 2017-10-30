package com.example.lipeng_ds3.myapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lipeng_ds3.myapp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/30.
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

    public synchronized static UserDatabase getInstance(Context context){
        if (userDatabase == null){
            userDatabase = new UserDatabase(context);
        }
        return userDatabase;
    }

    public List<User> getUser(){
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
