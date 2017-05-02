package com.newsreader.thenewsreader.viewpresenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.newsreader.thenewsreader.BaseActivity;
import com.newsreader.thenewsreader.R;
import com.newsreader.thenewsreader.annotations.DaggerScope;
import com.newsreader.thenewsreader.annotations.Layout;
import com.newsreader.thenewsreader.appfactory.ScreenComponentFactory;
import com.newsreader.thenewsreader.application.AppDependencies;
import com.newsreader.thenewsreader.utils.Utils;
import com.newsreader.thenewsreader.views.SplashView;
import com.tapadoo.alerter.Alerter;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.History;
import flow.path.Path;
import mortar.ViewPresenter;

/**
 * Created by rkodekar on 4/30/17.
 */

@Layout(R.layout.splash_screen)
public class SplashScreen extends Path implements ScreenComponentFactory<BaseActivity.Component> {

    @Override
    public Object createComponent(BaseActivity.Component parent) {
        return DaggerSplashScreen_Component.builder().component(parent).module(new Module()).build();
    }

    @dagger.Component(dependencies = BaseActivity.Component.class, modules = Module.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(SplashView view);
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(Component.class)
        public Presenter providePresenter() {
            return new Presenter();
        }
    }

    public static class Presenter extends ViewPresenter<SplashView> {

        public void gotoContent(SharedPreferences sharedPreferences) {
            boolean isfirstLaunch = sharedPreferences.getBoolean(Utils.FIRST_LAUNCH, true);
            if (isfirstLaunch) {
                sharedPreferences.edit().putBoolean(Utils.FIRST_LAUNCH, false).apply();
                Log.d("SplashScreen", "Is first launch");

            } else {
                Log.d("SplashScreen", "Is not first launch");
                Flow flow = Flow.get(getView().getContext());
                flow.setHistory(History.emptyBuilder()
                        .push(new SourceScreen())
                        .build(), Flow.Direction.FORWARD);
            }
        }
    }

}
