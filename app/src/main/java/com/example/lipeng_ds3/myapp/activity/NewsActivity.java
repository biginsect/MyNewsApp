package com.example.lipeng_ds3.myapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.lipeng_ds3.myapp.R;
import com.example.lipeng_ds3.myapp.util.HTMLFormat;
import com.example.lipeng_ds3.myapp.util.NetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 */

public class NewsActivity extends AppCompatActivity {
    private static final String TAG = "NewsActivity";
    @BindView(R.id.news_detail)  WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_page);

        ButterKnife.bind(this);
        if (!NetworkUtil.isNetworkAvailable(NewsActivity.this))
            Toast.makeText(this, "No Available Network!",Toast.LENGTH_LONG).show();
        else{
            Log.d(TAG, "Network is available!");
        }
        init();
    }

    private String loadHtmlCode(){
        SharedPreferences preferences = getSharedPreferences("html", MODE_PRIVATE);
        return preferences.getString("html","");
    }

    private void init(){
        //当前news的url
        String address = getIntent().getStringExtra("address");
        NetworkUtil.loadWebViewFromURL(this, address);
//        Log.d(TAG, "  " + address);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.loadDataWithBaseURL("/data/data/com.example.lipeng_ds3/shared_prefs/css.css",
                HTMLFormat.setNewContent(loadHtmlCode()), "text/html", "UTF-8", null);
    }
}
