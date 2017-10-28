package com.example.lipeng_ds3.myapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lipeng_ds3.myapp.R;
import com.example.lipeng_ds3.myapp.adapter.MyAdapter;
import com.example.lipeng_ds3.myapp.model.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.news_rv) RecyclerView mRecyclerView;

    private List<News> newsList = new ArrayList<>();
    private MyAdapter adapter;
    private News news;
    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(this, newsList);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击启动详情页面
                news = newsList.get(position);
                //获取news 的id，添加到url中，跳转到对应的详情页面
                newsId = news.getNewsId();
                //先测试能否跳转
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }
}
