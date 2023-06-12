package com.example.news.api;

import com.google.gson.annotations.SerializedName;

public class NewsId {
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
