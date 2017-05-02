package com.newsreader.thenewsreader.utils;

import android.view.View;

import com.newsreader.thenewsreader.modifiers.HandlesBack;

import flow.Flow;

/**
 * Created by rkodekar on 4/30/17.
 */

public class BackSupport {

    public static boolean onBackPressed(View childView) {
        if (childView instanceof HandlesBack) {
            if (((HandlesBack) childView).onBackPressed()) {
                return true;
            }
        }
        return Flow.get(childView).goBack();
    }

    private BackSupport() {
    }
}
