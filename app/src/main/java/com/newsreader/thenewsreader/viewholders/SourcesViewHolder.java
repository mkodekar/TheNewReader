package com.newsreader.thenewsreader.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.models.Source;
import com.newsreader.thenewsreader.utils.Logos;
import com.squareup.picasso.Picasso;
import com.thebrownarrow.customfont.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rkodekar on 4/30/17.
 */

public class SourcesViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.source_image)
    ImageView sourceimage;

    @BindView(R.id.source_name)
    CustomFontTextView sourcename;


    public SourcesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



    public void bind(Source source) {
        RxView.clicks(itemView).subscribe(aVoid -> {
            Log.d("clicked source is " , source.getName());
        });
        Logos logos = new Logos();
        Picasso.with(itemView.getContext()).load(logos.getLogmaps().get(source.getId())).into(sourceimage);
        sourcename.setText(source.getName());
    }
}
