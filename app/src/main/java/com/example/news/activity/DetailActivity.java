package com.example.news.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.news.R;
import com.example.news.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    private String title;
    private String urlToImage;
    private String author;
    private String publishedAt;
    private String content;
    private String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);

        title = getIntent().getStringExtra("title");
        urlToImage = getIntent().getStringExtra("urlToImage");
        author = getIntent().getStringExtra("author");
        publishedAt = getIntent().getStringExtra("publishedAt");
        content = getIntent().getStringExtra("content");
        source = getIntent().getStringExtra("source");

        setNewsDetails();

        binding.detailToolbar.setOnClickListener(v-> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    private void setNewsDetails(){
        binding.loadingDetail.setVisibility(View.GONE);
        binding.verticalLayout.setVisibility(View.VISIBLE);

        binding.textTitle.setText(title);

        if(author != null){
            binding.textAuthor.setText("Author : " + author);
        } else {
            binding.textAuthor.setText("Author : -");
        }

        if(publishedAt != null){
            binding.textDatePublished.setText("Date : " + publishedAt);
        } else {
            binding.textDatePublished.setText("Date : -");
        }

        if(content != null){
            binding.textContent.setText(content);
        } else {
            binding.textContent.setText("");
        }

        if(source != null){
            binding.textSource.setText("Source URL : " + source);
        } else {
            binding.textSource.setText("Source URL : -");
        }

        if(urlToImage != null){
            Uri imgUrl = Uri.parse(urlToImage);
            Picasso.get().load(imgUrl).into(binding.imageNews);
        } else {
            binding.imageNews.setImageResource(R.drawable.ic_no_image);
        }

    }
}