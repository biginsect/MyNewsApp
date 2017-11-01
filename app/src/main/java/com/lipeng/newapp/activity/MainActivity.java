package com.lipeng.newapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lipeng_ds3.newsapp.R;
import com.lipeng.newapp.adapter.MyAdapter;
import com.lipeng.newapp.database.NewsDatabase;
import com.lipeng.newapp.model.News;
import com.lipeng.newapp.util.NetworkUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * App主界面，从url请求数据之后将相关数据显示在页面上
 * 主要有RecyclerView显示新闻的title、content和image，点击item能跳转到新闻详情页WebView
 * 第一次url请求得到的是image的url，需要再次请求才能得到图片*/

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    @BindView(R.id.news_rv) RecyclerView mRecyclerView;

    private List<News> newsList ;
    private MyAdapter adapter;
    private NewsDatabase mDatabase;

    private News news;
    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!NetworkUtil.isNetworkAvailable(MainActivity.this))
            Toast.makeText(this, "No Available Network!",Toast.LENGTH_LONG).show();
        else
            Log.d(TAG, "Network is available!");

        init();
    }

    private void init(){//初始化控件，加载数据等
        ButterKnife.bind(this);
        Fresco.initialize(this);
        //存放news的List
        newsList = new ArrayList<>();
        mDatabase = NewsDatabase.getInstance(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //保持RecyclerView固定的大小
        mRecyclerView.setHasFixedSize(true);
        //请求数据，当请求成功则向数据库读取内容
        if(NetworkUtil.getContentFromURL(mDatabase, NetworkUtil.NEWS_URL)) {
            newsList = mDatabase.loadNews();
        }
//        Log.d(TAG, "news size is " + newsList.size());
        adapter = new MyAdapter(this, newsList);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, News position) {
                //点击启动详情页面
                news = position;
                //获取news 的id，添加到url中，跳转到对应的详情页面
                newsId = news.getNewsId();
                Log.d(TAG, newsId + "");
                //先测试能否跳转
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("address",NetworkUtil.URL_HAS_NOT_ID + newsId);
                startActivity(intent);
            }
        });
    }

}
