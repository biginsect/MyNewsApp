package com.example.lipeng_ds3.myapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lipeng_ds3.myapp.R;
import com.example.lipeng_ds3.myapp.database.MyDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 */

public class LoginPageActivity extends AppCompatActivity implements View.OnClickListener{
    private MyDatabaseHelper mDBHelper;
    private String account;
    private String password;
    private String getAccount;
    private String getPassword;

    //控件
    @BindView(R.id.account) EditText accountEdit;
    @BindView(R.id.password) EditText passwordEdit;
    @BindView(R.id.login)  Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);
        loginBtn.setOnClickListener(this);
        mDBHelper = new MyDatabaseHelper(this);
        getUserFromDB();
    }

    private void getUserFromDB(){//从数据库读取内容
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        Cursor cursor = database.query("user", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
         do{
             account = cursor.getString(cursor.getColumnIndex("account"));
             password = cursor.getString(cursor.getColumnIndex("password"));
         }while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                getAccount = accountEdit.getText().toString();
                getPassword = passwordEdit.getText().toString();
                if (getAccount == null){//检查EditText内容是否为空
                    Toast.makeText(this, "account must not be null!",Toast.LENGTH_SHORT ).show();
                }else if (account.equals(getAccount) && password.equals(getPassword)){
                    Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "account or password is invalid!",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
