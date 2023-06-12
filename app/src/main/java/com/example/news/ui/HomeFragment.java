package com.example.news.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.adapter.NewsEverythingAdapter;
import com.example.news.api.ApiClient;
import com.example.news.api.ApiService;
import com.example.news.api.NewsResponse;
import com.example.news.api.NewsResult;
import com.example.news.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private ApiService apiService;
    private FragmentHomeBinding binding;
    private NewsEverythingAdapter newsEverythingAdapter;
    private final List<NewsResult> newsResultList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);

        setNewsData(root);
        return root;
    }

    private void setNewsData(View root) {
        RecyclerView rvAllNews = root.findViewById(R.id.rvAllNews);
        newsEverythingAdapter = new NewsEverythingAdapter(newsResultList, getContext());

        rvAllNews.setAdapter(newsEverythingAdapter);
        getNewsData();
    }

    private void getNewsData() {
        String apiKey = "71593d94a9394f10a6810987d49396ff";
        String q = "trending";
        String language = "en";

        Call<NewsResponse> call = apiService.getEverything(apiKey, q, language);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.body() != null){
                    int pageCount = newsResultList.size();
                    binding.loadingAll.setVisibility(View.GONE);
                    binding.rvAllNews.setVisibility(View.VISIBLE);

                    newsResultList.addAll(response.body().getResults());
                    newsEverythingAdapter.notifyItemChanged(pageCount, newsResultList.size());
                } else {
                    binding.loadingAll.setVisibility(View.GONE);
                    binding.textNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                binding.loadingAll.setVisibility(View.GONE);
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