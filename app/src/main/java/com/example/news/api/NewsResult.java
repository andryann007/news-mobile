package com.example.news.api;

import com.google.gson.annotations.SerializedName;

public class NewsResult {

    @SerializedName("id")
    int id;
    @SerializedName("source")
    private NewsId newsId;

    @SerializedName("author")
    String author;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("url")
    String url;

    @SerializedName("urlToImage")
    String urlToImage;

    @SerializedName("publishedAt")
    String publishedAt;

    @SerializedName("content")
    String content;

    public NewsId getNewsId() {
        return newsId;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }
}
