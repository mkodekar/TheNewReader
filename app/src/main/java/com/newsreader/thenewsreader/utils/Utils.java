package com.newsreader.thenewsreader.utils;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by rkodekar on 4/30/17.
 */

public class Utils {

    public static final String API_KEY = "a30d8a5ddb944651aa5093177836b6f1";
    public static final String BASE_URL = "https://newsapi.org/v1/";
    public static final String ARRAY_KEY = "selected_sources";
    public static final String FIRST_LAUNCH = "first_launch";
    public static final String APP_NAME = "TheNewsReader";

    public Utils() {
    }

    public interface OnMeasuredCallback {
        void onMeasured(View view, int width, int height);
    }

    public static void waitForMeasure(final View view, final OnMeasuredCallback callback) {
        int width = view.getWidth();
        int height = view.getHeight();

        if (width > 0 && height > 0) {
            callback.onMeasured(view, width, height);
            return;
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override public boolean onPreDraw() {
                final ViewTreeObserver observer = view.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.removeOnPreDrawListener(this);
                }

                callback.onMeasured(view, view.getWidth(), view.getHeight());

                return true;
            }
        });
    }

}
