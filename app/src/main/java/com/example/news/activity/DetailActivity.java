package com.example.news.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String url = getIntent().getStringExtra("url");
        if(!url.isEmpty()){
            loadNews(url);
        } else {
            url = "https://www.google.com";
            loadNews(url);
        }
    }

    private void loadNews(String url){
        binding.webView.getSettings().setJavaScriptEnabled(false);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl(url);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}