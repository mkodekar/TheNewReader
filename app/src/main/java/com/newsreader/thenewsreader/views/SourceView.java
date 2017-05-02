package com.newsreader.thenewsreader.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.modifiers.HandlesBack;
import com.newsreader.thenewsreader.services.DaggerService;
import com.newsreader.thenewsreader.viewpresenter.SourceScreen;
import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontTextView;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

/**
 * Created by rkodekar on 4/30/17.
 */

public class SourceView extends CoordinatorLayout implements SourceScreen.SourceViewProviders , HandlesBack {

    @Inject
    SourceScreen.Presenter presenter;

    @BindView(R.id._sources)
    IndexFastScrollRecyclerView sources_recyclerView;

    @BindView(R.id.loadingIndicator)
    AVLoadingIndicatorView loading;

    public SourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<SourceScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public void onAttachedToWindow() {
        ButterKnife.bind(this);
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }




    @Override
    public IndexFastScrollRecyclerView recyclerView() {
        return sources_recyclerView;
    }

    @Override
    public AVLoadingIndicatorView loadingView() {
        return loading;
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

}
