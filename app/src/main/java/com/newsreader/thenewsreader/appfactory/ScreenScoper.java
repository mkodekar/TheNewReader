package com.newsreader.thenewsreader.appfactory;

import android.content.Context;

import com.newsreader.thenewsreader.services.DaggerService;

import flow.path.Path;
import mortar.MortarScope;

/**
 * Created by rkodekar on 4/30/17.
 */

public class ScreenScoper {

    public MortarScope getScreenScope(Context context, String name, Path path) {
        MortarScope parentScoper = MortarScope.getScope(context);
        MortarScope childScoper = parentScoper.findChild(name);
        if (childScoper != null) {
            return childScoper;
        }

        if (!(path instanceof ScreenComponentFactory)) {
            throw new IllegalStateException("Path must imlement ComponentFactory");
        }
        ScreenComponentFactory screenComponentFactory = (ScreenComponentFactory) path;
        Object component = screenComponentFactory.createComponent(parentScoper.getService(DaggerService.SERVICE_NAME));

        MortarScope.Builder builder = parentScoper.buildChild()
                .withService(DaggerService.SERVICE_NAME, component);

        return builder.build(name);
    }
}
