package com.newsreader.thenewsreader.application;

import android.app.Application;

import com.newsreader.thenewsreader.annotations.DaggerScope;
import com.newsreader.thenewsreader.services.DaggerService;

import mortar.MortarScope;

/**
 * Created by rkodekar on 4/30/17.
 */

public class TheNewsReader extends Application {

    private MortarScope mortarScope;


    @Override
    public Object getSystemService(String name) {
        return  mortarScope.hasService(name) ? mortarScope.getService(name): super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Component component = DaggerTheNewsReader_Component.create();
        component.inject(this);

        mortarScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build("root");
    }

    @dagger.Component
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(TheNewsReader theNewsReader);
    }

}
