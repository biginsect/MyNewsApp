package com.example.lipeng_ds3.myapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lipeng_ds3.myapp.model.News;

import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private List<News> mNews;

    public MyAdapter(List<News> news){
        mNews = news;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView newsTitle;
        TextView newsContent;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
