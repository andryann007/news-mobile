package com.example.news.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.adapter.NewsTopHeadlinesAdapter;
import com.example.news.api.ApiClient;
import com.example.news.api.ApiService;
import com.example.news.api.NewsResponse;
import com.example.news.api.NewsResult;
import com.example.news.databinding.FragmentTrendingBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrendingFragment extends Fragment {

    private ApiService apiService;
    private FragmentTrendingBinding binding;
    private NewsTopHeadlinesAdapter newsTopHeadlinesAdapter;
    private final List<NewsResult> newsResultList = new ArrayList<>();

    public TrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentTrendingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);

        setNewsData(root);
        return root;
    }

    private void setNewsData(View root) {
        RecyclerView rvTrendingNews = root.findViewById(R.id.rvTrendingNews);
        newsTopHeadlinesAdapter = new NewsTopHeadlinesAdapter(newsResultList, getContext());

        rvTrendingNews.setAdapter(newsTopHeadlinesAdapter);
        getNewsData();
    }

    private void getNewsData() {
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String language = "en";

        Call<NewsResponse> call = apiService.getTopHeadlines(apiKey, language);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = newsResultList.size();
                    binding.loadingTrending.setVisibility(View.GONE);
                    binding.rvTrendingNews.setVisibility(View.VISIBLE);

                    newsResultList.addAll(response.body().getResults());
                    newsTopHeadlinesAdapter.notifyItemChanged(pageCount, newsResultList.size());
                } else {
                    binding.loadingTrending.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingTrending.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Fail to Fetch The Data !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}