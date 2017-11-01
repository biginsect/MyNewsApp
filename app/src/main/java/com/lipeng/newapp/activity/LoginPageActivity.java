package com.lipeng.newapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lipeng_ds3.newsapp.R;
import com.lipeng.newapp.database.UserDatabase;
import com.lipeng.newapp.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 * email:  lipeng-ds3@gomeplus.com
 * App登录界面，主要实现账号密码的验证以及之后的跳转
 */

public class LoginPageActivity extends AppCompatActivity implements View.OnClickListener{
    private UserDatabase database;
    private User user;
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
        getUserFromDB();
    }

    private void getUserFromDB(){//从数据库读取账号密码
        database = UserDatabase.getInstance(this);
        user = database.getUser().get(0);
        account = user.getAccount();
        password = user.getPassword();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;
            default:
                break;
        }
    }

    private void login(){
        //获取EditText的账号和密码
        getAccount = accountEdit.getText().toString();
        getPassword = passwordEdit.getText().toString();
        if ((TextUtils.isEmpty(getAccount))
                || (TextUtils.isEmpty(getPassword))){//检查EditText内容是否为空
            Toast.makeText(this, "account or password must not be null!",Toast.LENGTH_SHORT ).show();
        }else if (account.equals(getAccount) && password.equals(getPassword)){//账号密码都正确则跳转
            Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
            Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "account or password is invalid!",Toast.LENGTH_SHORT).show();
        }
    }
}
