package com.newsreader.thenewsreader.viewpresenter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.newsreader.thenewsreader.BaseActivity;
import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.adapters.SourcesAdapter;
import com.newsreader.thenewsreader.annotations.DaggerScope;
import com.newsreader.thenewsreader.annotations.Layout;
import com.newsreader.thenewsreader.appfactory.ScreenComponentFactory;
import com.newsreader.thenewsreader.application.AppDependencies;
import com.newsreader.thenewsreader.contextpackage.ContextHolder;
import com.newsreader.thenewsreader.models.Source;
import com.newsreader.thenewsreader.models.Sources;
import com.newsreader.thenewsreader.mvp.SourceInterface;
import com.newsreader.thenewsreader.mvp.SourcePresenter;
import com.newsreader.thenewsreader.services.ProvideRetrofit;
import com.newsreader.thenewsreader.services.RetrofitCreator;
import com.newsreader.thenewsreader.views.SourceView;
import com.tapadoo.alerter.Alerter;
import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontTextView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.path.Path;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import mortar.ViewPresenter;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by rkodekar on 4/30/17.
 */

@Layout(R.layout.source_selector)
public class SourceScreen extends Path implements ScreenComponentFactory<BaseActivity.Component> {


    @Override
    public Object createComponent(BaseActivity.Component parent) {
        return DaggerSourceScreen_Component.builder().component(parent).module(new Module()).build();
    }


    @dagger.Component(dependencies = BaseActivity.Component.class, modules = Module.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(SourceView sourceView);
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(Component.class)
        public Presenter providePresenter() {
            return new Presenter();
        }
    }

    public static class Presenter extends ViewPresenter<SourceView> implements SourceInterface {

        private SourcePresenter sourcePresenter;
        private ArrayList<Source> sources;
        private RetrofitCreator retrofitCreator;

        public Presenter() {
            this.retrofitCreator = new RetrofitCreator();
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            outState.putParcelableArrayList("source", sources);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            sourcePresenter = new SourcePresenter(this);
            if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("source") != null) {
                ArrayList<Source> sources = savedInstanceState.getParcelableArrayList("source");
                int display_mode = getView().getContext().getResources().getConfiguration().orientation;
                getView().recyclerView().setVisibility(View.VISIBLE);
                if (getView().recyclerView() != null) {
                    SourcesAdapter sourcesAdapter = new SourcesAdapter(sources);
                    getView().recyclerView().setAdapter(sourcesAdapter);
                    if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
                        getView().recyclerView().setLayoutManager(new GridLayoutManager(getView().getContext(), 2));
                    } else {
                        getView().recyclerView().setLayoutManager(new GridLayoutManager(getView().getContext(), 3));
                    }
                    getView().recyclerView().setIndexTextSize(15);
                    getView().recyclerView().setIndexBarColor("#f4f4f4");
                    getView().recyclerView().setIndexBarTextColor("#020202");
                    getView().recyclerView().setIndexBarCornerRadius(3);
                    Typeface typeface = Typeface.createFromAsset(getView().getContext().getAssets(), "tomnr.ttf");
                    getView().recyclerView().setTypeface(typeface);
                    getView().recyclerView().setScrollbarFadingEnabled(true);
                    Log.d("RecyclerView", "Recyclerview is not null");
                } else {
                    Log.e("Recyclerview","RecyclerView is null");
                }
            } else {
                getView().loadingView().setVisibility(View.VISIBLE);
                getView().loadingView().show();
                sourcePresenter.fetchSources();
            }
        }

        @Override
        public void dropView(SourceView view) {
            super.dropView(view);
            sourcePresenter.dropView();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onFetchError(String error) {
            Log.d("Retrofit Sources fetch error", error);
            getView().loadingView().hide();
        }

        @Override
        public void onSuccessfullyFetched(Sources sources) {
            getView().recyclerView().setVisibility(View.VISIBLE);
            SourcesAdapter sourcesAdapter = new SourcesAdapter(sources.getSources());
            this.sources = sources.getSources();
            int display_mode = getView().getContext().getResources().getConfiguration().orientation;
            getView().loadingView().hide();
            if (sources.getStatus().equals("ok")) {
                getView().recyclerView().setAdapter(sourcesAdapter);
                if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
                    getView().recyclerView().setLayoutManager(new GridLayoutManager(getView().getContext(), 2));
                } else {
                    getView().recyclerView().setLayoutManager(new GridLayoutManager(getView().getContext(), 3));
                }
                getView().recyclerView().setIndexTextSize(15);
                getView().recyclerView().setIndexBarColor("#f4f4f4");
                getView().recyclerView().setIndexBarTextColor("#020202");
                getView().recyclerView().setIndexBarCornerRadius(3);
                Typeface typeface = Typeface.createFromAsset(getView().getContext().getAssets(), "tomnr.ttf");
                getView().recyclerView().setTypeface(typeface);
                getView().recyclerView().setScrollbarFadingEnabled(true);
            }
        }

        @Override
        public Observable<Sources> getSources() {
            return retrofitCreator.getRetrofitService().getSource();
        }
    }

    public interface SourceViewProviders extends ContextHolder {
        IndexFastScrollRecyclerView recyclerView();
        AVLoadingIndicatorView loadingView();
    }

}
