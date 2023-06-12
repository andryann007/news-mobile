package com.example.news.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import com.example.news.R;
import com.example.news.adapter.NewsEverythingAdapter;
import com.example.news.api.ApiClient;
import com.example.news.api.ApiService;
import com.example.news.api.NewsResponse;
import com.example.news.api.NewsResult;
import com.example.news.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private NewsEverythingAdapter searchAdapter;
    private ApiService apiService;
    private ActivitySearchBinding binding;

    private String query;

    private final List<NewsResult> searchResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);

        apiService = ApiClient.getClient().create(ApiService.class);

        query = getIntent().getStringExtra("searchQuery");
        String searchType = getIntent().getStringExtra("searchIn");
        setSearchTitle(binding.searchToolbar, query);

        searchAdapter = new NewsEverythingAdapter(searchResultList, this);

        if(searchType.equalsIgnoreCase("title")){
            searchNewsByTitle();
        } else if (searchType.equalsIgnoreCase("description")){
            searchNewsByDesc();
        }

        binding.searchToolbar.setOnClickListener(v-> onBackPressed());
    }
    private void searchNewsByTitle(){
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String language = "en";
        String searchIn = "title";

        Call<NewsResponse> call = apiService.searchNews(apiKey, query, language, searchIn);
        call.enqueue(new Callback<NewsResponse>(){

            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = searchResultList.size();

                    binding.loadingSearch.setVisibility(View.GONE);
                    binding.rvSearchNews.setAdapter(searchAdapter);
                    binding.rvSearchNews.setVisibility(View.VISIBLE);

                    searchResultList.addAll(response.body().getResults());
                    searchAdapter.notifyItemRangeInserted(pageCount, searchResultList.size());
                } else {
                    binding.loadingSearch.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingSearch.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchNewsByDesc(){
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String language = "en";
        String searchIn = "description";

        Call<NewsResponse> call = apiService.searchNews(apiKey, query, language, searchIn);
        call.enqueue(new Callback<NewsResponse>(){

            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = searchResultList.size();

                    binding.loadingSearch.setVisibility(View.GONE);
                    binding.rvSearchNews.setVisibility(View.VISIBLE);

                    searchResultList.addAll(response.body().getResults());
                    searchAdapter.notifyItemRangeInserted(pageCount, searchResultList.size());
                } else {
                    binding.loadingSearch.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingSearch.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSearchTitle(Toolbar toolbar, String textValue){
        toolbar.setTitle(HtmlCompat.fromHtml("Search News For : <b>" + textValue + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}