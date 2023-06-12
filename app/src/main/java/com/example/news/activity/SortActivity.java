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
import com.example.news.databinding.ActivitySortBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SortActivity extends AppCompatActivity {

    private NewsEverythingAdapter sortAdapter;
    private ApiService apiService;
    private ActivitySortBinding binding;

    private final List<NewsResult> sortResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySortBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.sortToolbar);
        setSupportActionBar(toolbar);

        apiService = ApiClient.getClient().create(ApiService.class);

        String sortType = getIntent().getStringExtra("sortBy");
        setSortTitle(binding.sortToolbar, sortType);

        sortAdapter = new NewsEverythingAdapter(sortResultList, this);

        if(sortType.equalsIgnoreCase("popularity")){
            sortNewsByPopularity();
        } else if (sortType.equalsIgnoreCase("publishedAt")){
            sortNewsByDate();
        }

        binding.sortToolbar.setOnClickListener(v -> onBackPressed());
    }

    private void sortNewsByPopularity(){
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String q = "trending";
        String language = "en";
        String sortBy = "popularity";

        Call<NewsResponse> call = apiService.sortNews(apiKey, q, language, sortBy);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = sortResultList.size();

                    binding.loadingSort.setVisibility(View.GONE);
                    binding.rvSortNews.setAdapter(sortAdapter);
                    binding.rvSortNews.setVisibility(View.VISIBLE);

                    sortResultList.addAll(response.body().getResults());
                    sortAdapter.notifyItemRangeInserted(pageCount, sortResultList.size());
                } else {
                    binding.loadingSort.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingSort.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortNewsByDate(){
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String q = "trending";
        String language = "en";
        String sortBy = "publishedAt";

        Call<NewsResponse> call = apiService.sortNews(apiKey, q, language, sortBy);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = sortResultList.size();

                    binding.loadingSort.setVisibility(View.GONE);
                    binding.rvSortNews.setVisibility(View.VISIBLE);

                    sortResultList.addAll(response.body().getResults());
                    sortAdapter.notifyItemRangeInserted(pageCount, sortResultList.size());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingSort.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setSortTitle(Toolbar toolbar, String textValue){
        toolbar.setTitle(HtmlCompat.fromHtml("Sort News By : <b>" + textValue + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}