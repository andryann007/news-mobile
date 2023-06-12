package com.example.news.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

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

        if(filterCountry.isEmpty()){
            setCategoryTitle(binding.filterToolbar, filterCategory);
            filterCategory();
        } else if (filterCategory.isEmpty()){
            setCountryTitle(binding.filterToolbar, filterCountry);
            filterCountry();
        } else {
            setCategoryCountryTitle(binding.filterToolbar, filterCategory, filterCountry);
            filterCategoryAndCountry();
        }

        binding.filterToolbar.setOnClickListener(v -> onBackPressed());
    }
    private void filterCategory() {
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String language = "en";

        Call<NewsResponse> call = apiService.filterCategory(apiKey, filterCategory, language);
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

    private void filterCountry() {
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String language = "en";

        Call<NewsResponse> call = apiService.filterCountry(apiKey, filterCountry, language);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = filterResultList.size();

                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.rvFilterNews.setAdapter(filterAdapter);
                    binding.rvFilterNews.setVisibility(View.VISIBLE);

                    filterResultList.addAll(response.body().getResults());
                    filterAdapter.notifyItemChanged(pageCount, filterResultList.size());
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

    private void filterCategoryAndCountry() {
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String language = "en";

        Call<NewsResponse> call = apiService.filterCategoryAndCountry(apiKey, filterCategory, filterCountry, language);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = filterResultList.size();

                    binding.loadingFIlter.setVisibility(View.GONE);
                    binding.rvFilterNews.setAdapter(filterAdapter);
                    binding.rvFilterNews.setVisibility(View.VISIBLE);

                    filterResultList.addAll(response.body().getResults());
                    filterAdapter.notifyItemChanged(pageCount, filterResultList.size());
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

    private void setCategoryTitle(Toolbar toolbar, String textValue){
        toolbar.setTitle(HtmlCompat.fromHtml("Filter News By : <b>" + textValue + "</b> Category", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setCountryTitle(Toolbar toolbar, String textValue){
        toolbar.setTitle(HtmlCompat.fromHtml("Filter News By : <b>" + textValue + "</b> Country", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void setCategoryCountryTitle(Toolbar toolbar, String textValue, String textValue2){
        toolbar.setTitle(HtmlCompat.fromHtml("Filter News By : <b>" + textValue + "</b> Category and <b>" + textValue2 + "</b> Country", HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}