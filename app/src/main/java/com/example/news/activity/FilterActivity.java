package com.example.news.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.adapter.NewsTopHeadlinesAdapter;
import com.example.news.api.ApiClient;
import com.example.news.api.ApiService;
import com.example.news.api.NewsResponse;
import com.example.news.api.NewsResult;
import com.example.news.databinding.ActivityFilterBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    private NewsTopHeadlinesAdapter filterAdapter;
    private ApiService apiService;
    private ActivityFilterBinding binding;
    private final List<NewsResult> filterResultList = new ArrayList<>();
    private int page = 1;
    private final int pageSize = 20;
    private final String apiKey = "71593d94a9394f10a6810987d49396ff";
    private final String language = "en";
    private String filterCategory;
    private String filterCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.filterToolbar);
        setSupportActionBar(toolbar);

        apiService = ApiClient.getClient().create(ApiService.class);

        filterCategory = getIntent().getStringExtra("category");
        filterCountry = getIntent().getStringExtra("country");

        filterAdapter = new NewsTopHeadlinesAdapter(filterResultList, this);

        if(filterCountry == null){
            setCategoryTitle(binding.filterToolbar, filterCategory);
            filterCategory(page);

            binding.rvFilterNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if(!binding.rvFilterNews.canScrollVertically(1)){
                        page++;
                        filterCategory(page);
                    }
                }
            });
        } else if (filterCategory == null){
            setCountryTitle(binding.filterToolbar, filterCountry);
            filterCountry(page);

            binding.rvFilterNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if(!binding.rvFilterNews.canScrollVertically(1)){
                        page++;
                        filterCategory(page);
                    }
                }
            });
        } else {
            setCategoryCountryTitle(binding.filterToolbar, filterCategory, filterCountry);
            filterCategoryAndCountry(page);

            binding.rvFilterNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if(!binding.rvFilterNews.canScrollVertically(1)){
                        page++;
                        filterCategoryAndCountry(page);
                    }
                }
            });
        }

        binding.filterToolbar.setOnClickListener(v -> onBackPressed());
    }
    private void filterCategory(int page) {
        Call<NewsResponse> call = apiService.filterCategory(apiKey, filterCategory, language, page, pageSize);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = filterResultList.size();

                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.rvFilterNews.setAdapter(filterAdapter);
                    binding.rvFilterNews.setVisibility(View.VISIBLE);

                    filterResultList.addAll(response.body().getResults());
                    filterAdapter.notifyItemRangeInserted(pageCount, filterResultList.size());
                } else {
                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingFIlter.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterCountry(int page) {
        Call<NewsResponse> call = apiService.filterCountry(apiKey, filterCountry, language, page, pageSize);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = filterResultList.size();

                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.rvFilterNews.setAdapter(filterAdapter);
                    binding.rvFilterNews.setVisibility(View.VISIBLE);

                    filterResultList.addAll(response.body().getResults());
                    filterAdapter.notifyItemRangeInserted(pageCount, filterResultList.size());
                } else {
                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingFIlter.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterCategoryAndCountry(int page) {
        Call<NewsResponse> call = apiService.filterCategoryAndCountry(apiKey, filterCategory, filterCountry, language, page, pageSize);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = filterResultList.size();

                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.rvFilterNews.setAdapter(filterAdapter);
                    binding.rvFilterNews.setVisibility(View.VISIBLE);

                    filterResultList.addAll(response.body().getResults());
                    filterAdapter.notifyItemRangeInserted(pageCount, filterResultList.size());
                } else {
                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingFIlter.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCategoryTitle(Toolbar toolbar, String category){
        toolbar.setTitle(HtmlCompat.fromHtml("Filter : <b>" + category + "</b> category", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setCountryTitle(Toolbar toolbar, String country){
        toolbar.setTitle(HtmlCompat.fromHtml("Filter : <b>" + country + "</b> country", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setCategoryCountryTitle(Toolbar toolbar, String category, String country){
        toolbar.setTitle(HtmlCompat.fromHtml("Filter : <b>" + category + "</b> category in <b>" + country + "</b> country", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}