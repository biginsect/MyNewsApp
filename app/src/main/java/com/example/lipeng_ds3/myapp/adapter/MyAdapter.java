package com.example.lipeng_ds3.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lipeng_ds3.myapp.R;
import com.example.lipeng_ds3.myapp.model.News;

import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener{
    private List<News> mNews;
    private OnItemClickListener mOnItemClickListener = null;

    public MyAdapter(Context context, List<News> news){
        mNews = news;
    }

    public  interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        News news = mNews.get(position);
//        holder.newsImage.setImageResource(news.getNewsImageUrl());
//        holder.newsTitle.setText(news.getNewsTitle());
//        holder.newsContent.setText(news.getNewsContent());
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            //getTag 获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    //自定义ViewHolder
    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView newsTitle;
        TextView newsContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            newsImage = (ImageView)itemView.findViewById(R.id.news_image);
            newsTitle = (TextView)itemView.findViewById(R.id.news_title);
            newsContent = (TextView)itemView.findViewById(R.id.news_content);
        }
    }

}
