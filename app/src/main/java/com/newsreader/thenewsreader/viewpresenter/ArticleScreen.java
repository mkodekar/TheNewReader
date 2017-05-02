package com.newsreader.thenewsreader.viewpresenter;

import android.os.Bundle;

import com.newsreader.thenewsreader.BaseActivity;
import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.annotations.DaggerScope;
import com.newsreader.thenewsreader.annotations.Layout;
import com.newsreader.thenewsreader.appfactory.ScreenComponentFactory;
import com.newsreader.thenewsreader.services.RetrofitCreator;
import com.newsreader.thenewsreader.views.ArticlesView;

import dagger.Provides;
import flow.path.Path;
import mortar.ViewPresenter;

/**
 * Created by rkodekar on 5/2/17.
 */


@Layout(R.layout.articles_content)
public class ArticleScreen extends Path implements ScreenComponentFactory<BaseActivity.Component>{

    private String id;
    private BaseActivity activity;
    private RetrofitCreator retrofitCreator;

    public ArticleScreen(String id) {
        this.id = id;
        this.activity = new BaseActivity();
        retrofitCreator = new RetrofitCreator();
    }

    @Override
    public Object createComponent(BaseActivity.Component parent) {
        return DaggerArticleScreen_Component.builder().component(parent).module(new Module()).build();
    }


    @dagger.Component(dependencies = BaseActivity.Component.class, modules = Module.class)
    @DaggerScope(Component.class)
    public interface Component {
        void inject(ArticlesView articlesView);
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(Component.class)
        public Presenter providePresenter() {
            return new Presenter(id, activity);
        }
    }

    public static class Presenter extends ViewPresenter<ArticlesView> {

        private String id;
        private RetrofitCreator retrofitCreator;
        private BaseActivity activity;

        public Presenter(String id, BaseActivity activity) {
            this.id = id;
            this.activity = activity;
            this.retrofitCreator = new RetrofitCreator();
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            activity = new BaseActivity();
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
        }

    }
}
