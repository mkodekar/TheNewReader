package com.newsreader.thenewsreader.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.models.Article;
import com.newsreader.thenewsreader.viewholders.ArticlesViewHolder;

import java.util.ArrayList;

/**
 * Created by rkodekar on 5/2/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesViewHolder> {
    private ArrayList<Article> articles;

    public ArticlesAdapter(ArrayList<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_row,null);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
