package com.newsreader.thenewsreader.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.newsreader.thenewsreader.services.DaggerService;
import com.newsreader.thenewsreader.utils.Utils;
import com.newsreader.thenewsreader.viewpresenter.SplashScreen;

import javax.inject.Inject;

/**
 * Created by rkodekar on 4/30/17.
 */

public class SplashView extends CoordinatorLayout {

    @Inject SplashScreen.Presenter presenter;

    SharedPreferences sharedPreferences;

    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<SplashScreen.Component>getDaggerComponent(context).inject(this);
        sharedPreferences = context.getSharedPreferences(Utils.APP_NAME, 0);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
        presenter.gotoContent(sharedPreferences);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }
}
