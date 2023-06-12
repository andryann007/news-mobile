package com.example.news.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org").addConverterFactory(
                    GsonConverterFactory.create()
            ).build();
        }
        return retrofit;
    }
}
