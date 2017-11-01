package com.example.lipeng_ds3.myapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lipeng_ds3.myapp.R;
import com.example.lipeng_ds3.myapp.adapter.MyAdapter;
import com.example.lipeng_ds3.myapp.database.NewsDatabase;
import com.example.lipeng_ds3.myapp.model.News;
import com.example.lipeng_ds3.myapp.util.NetworkUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

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
        if (!isNetworkAvailable(MainActivity.this))
            Toast.makeText(this, "No Available Network!",Toast.LENGTH_LONG).show();
        else
            Log.d(TAG, "Network is available!");
        init();
    }

    private void init(){//初始化控件，加载数据等
        ButterKnife.bind(this);
        Fresco.initialize(this);
        newsList = new ArrayList<>();
        mDatabase = NewsDatabase.getInstance(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        //加载数据
        //不应该每次都根据url去请求数据
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
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("address",NetworkUtil.URL_HAS_NOT_ID + newsId);
                startActivity(intent);
            }
        });
    }

    //检查网络状态
    public boolean isNetworkAvailable(Activity activity){
        Context context = activity.getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info.getState() == NetworkInfo.State.CONNECTED)
                return true;
        }
        return false;
    }
}
