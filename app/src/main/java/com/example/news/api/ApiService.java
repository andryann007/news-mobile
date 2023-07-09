package com.example.news.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v2/everything")
    Call<NewsResponse> getEverything(@Query("apiKey") String apiKey, @Query("q") String q,
                                     @Query("language") String language, @Query("page") int page,
                                     @Query("pageSize") int pageSize);

    @GET("/v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("apiKey") String apiKey, @Query("language") String language,
                                       @Query("page") int page, @Query("pageSize") int pageSize);

    @GET("/v2/everything")
    Call<NewsResponse> searchNews(@Query("apiKey") String apiKey, @Query("q") String q,
                                  @Query("language") String language, @Query("searchIn") String searchIn,
                                  @Query("page") int page, @Query("pageSize") int pageSize);

    @GET("/v2/everything")
    Call<NewsResponse> sortNews(@Query("apiKey") String apiKey, @Query("q") String q,
                                @Query("language") String language, @Query("sortBy") String sortBy,
                                @Query("page") int page, @Query("pageSize") int pageSize);

    @GET("/v2/top-headlines")
    Call<NewsResponse> filterCategory(@Query("apiKey") String apiKey, @Query("category") String category,
                                      @Query("language") String language, @Query("page") int page,
                                      @Query("pageSize") int pageSize);

    @GET("/v2/top-headlines")
    Call<NewsResponse> filterCountry(@Query("apiKey") String apiKey, @Query("country") String country,
                                     @Query("language") String language, @Query("page") int page,
                                     @Query("pageSize") int pageSize);

    @GET("/v2/top-headlines")
    Call<NewsResponse> filterCategoryAndCountry(@Query("apiKey") String apiKey, @Query("category") String category,
                                                @Query("country") String country, @Query("language") String language,
                                                @Query("page") int page, @Query("pageSize") int pageSize);
}
