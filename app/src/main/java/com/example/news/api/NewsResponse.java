package com.example.news.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsResponse {
    @SerializedName("status")
    String status;

    @SerializedName("totalResults")
    int totalResults;

    @SerializedName("articles")
    private final List<NewsResult> results = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<NewsResult> getResults() {
        return results;
    }
}
