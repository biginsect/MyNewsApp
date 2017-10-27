package com.example.lipeng_ds3.myapp.model;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 */

public class News {
    private int newsId;
    private int newsImageId;
    private String newsTitle;
    private String newsContent;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setNewsImageId(int newsImageId) {
        this.newsImageId = newsImageId;
    }

    public int getNewsImageId() {
        return newsImageId;
    }

    public void setNewsImage(int newsImageId) {
        this.newsImageId = newsImageId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
