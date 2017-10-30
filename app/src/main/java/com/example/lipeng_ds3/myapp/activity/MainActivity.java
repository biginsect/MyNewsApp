package com.example.lipeng_ds3.myapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.lipeng_ds3.myapp.R;
import com.example.lipeng_ds3.myapp.adapter.MyAdapter;
import com.example.lipeng_ds3.myapp.database.NewsDatabase;
import com.example.lipeng_ds3.myapp.model.News;
import com.example.lipeng_ds3.myapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        newsList = new ArrayList<>();
        ButterKnife.bind(this);
        mDatabase = NewsDatabase.getInstance(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        //加载数据
        NetworkUtil.getContentFromURL(mDatabase, NetworkUtil.NEWS_URL);
        //根据数据库中的id去请求content，然后对数据库对于的content进行更新
        updateNewsContent();
        newsList = mDatabase.loadNews();
        Log.d(TAG, "news size is " + newsList.size());
        adapter = new MyAdapter(this, newsList);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, News position) {
                //点击启动详情页面
                news = position;
                //获取news 的id，添加到url中，跳转到对应的详情页面
                newsId = news.getNewsId();
                //先测试能否跳转
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("newsId",newsId);
                startActivity(intent);
            }
        });
    }

    private void updateNewsContent(){
        List<Integer> idList = mDatabase.queryId();
        for (int i = 0; i < idList.size(); i++){
            int id = idList.get(i);
            NetworkUtil.getContentFromURLAndId(mDatabase,NetworkUtil.URL_HAS_NOT_ID, id);
        }
    }
}
