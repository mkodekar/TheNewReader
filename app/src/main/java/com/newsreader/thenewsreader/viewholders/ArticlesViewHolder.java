package com.newsreader.thenewsreader.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.models.Article;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by rkodekar on 5/2/17.
 */

public class ArticlesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.overlay)
    View overlay;

    @BindView(R.id.image)
    ImageView articlemage;

    @BindView(R.id.headline)
    TextView headline;

    @BindView(R.id.bookmark)
    ImageView bookmarkImageView;

    public ArticlesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Article article) {
        Picasso.with(itemView.getContext()).load(article.getUrlToImage()).into(articlemage);
        headline.setText(article.getTitle());
        RxView.clicks(bookmarkImageView).subscribe(aVoid -> Log.d("Bookmark button", "clicked"));
        RxView.clicks(itemView).subscribe(aVoid -> Log.d("Item View ", "clicked"));
    }
}
