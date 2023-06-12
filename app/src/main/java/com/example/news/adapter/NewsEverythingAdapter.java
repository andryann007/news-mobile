package com.example.news.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.activity.DetailActivity;
import com.example.news.api.NewsResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsEverythingAdapter extends RecyclerView.Adapter<NewsEverythingAdapter.NewsViewHolder> {
    private final List<NewsResult> newsResults;
    private final Context context;

    public NewsEverythingAdapter(List<NewsResult> newsResults, Context context){
        this.newsResults = newsResults;
        this.context = context;
    }
    @NonNull
    @Override
    public NewsEverythingAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsEverythingAdapter.NewsViewHolder holder, int position) {
        holder.bindItem(newsResults.get(position), context);
    }

    @Override
    public int getItemCount() {
        return newsResults.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{
        private final ImageView newsImage;
        private final TextView newsTitle, newsAuthor, newsPublished, newsDescription, newsSource;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.imageNews);
            newsTitle = itemView.findViewById(R.id.textTitle);
            newsAuthor = itemView.findViewById(R.id.textAuthor);
            newsPublished = itemView.findViewById(R.id.textDatePublished);
            newsDescription = itemView.findViewById(R.id.textDescription);
            newsSource = itemView.findViewById(R.id.textSource);
        }

        @SuppressLint("SetTextI18n")
        void bindItem(NewsResult newsResult, Context context){
            if(newsResult.getUrlToImage() == null){
                newsImage.setImageResource(R.drawable.ic_no_image);
            } else {
                Uri imgUrl = Uri.parse(newsResult.getUrlToImage());
                Picasso.get().load(imgUrl).into(newsImage);
            }

            String title = newsResult.getTitle();
            newsTitle.setText(title);

            String author = newsResult.getAuthor();
            if(author == null){
                newsAuthor.setText("Author : -");
            } else {
                newsAuthor.setText("Author : " + author);
            }

            String datePublished = newsResult.getPublishedAt();
            if(datePublished == null){
                newsPublished.setText("Date : -");
            } else {
                newsPublished.setText("Date : " + datePublished.substring(0, 10));
            }

            String description = newsResult.getDescription();
            if(description == null){
                newsDescription.setText("Description : No Description");
            } else {
                newsDescription.setText("Description : " + description);
            }

            String source = newsResult.getUrl();
            if(source == null){
                newsSource.setText("Source URL : No Source URL");
            } else {
                newsSource.setText("Source URL : " + source);
            }

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("title", newsResult.getTitle());
                i.putExtra("urlToImage", newsResult.getUrlToImage());
                i.putExtra("author", newsResult.getAuthor());
                i.putExtra("publishedAt", newsResult.getPublishedAt().substring(0, 10));
                i.putExtra("content", newsResult.getContent());
                i.putExtra("source", newsResult.getUrl());
                context.startActivity(i);
            });
        }
    }
}
