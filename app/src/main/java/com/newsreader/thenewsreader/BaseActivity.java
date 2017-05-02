package com.newsreader.thenewsreader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.newsreader.thenewsreader.annotations.DaggerScope;
import com.newsreader.thenewsreader.application.AppDependencies;
import com.newsreader.thenewsreader.application.TheNewsReader;
import com.newsreader.thenewsreader.callbacks.ActivityFinisher;
import com.newsreader.thenewsreader.modifiers.HandlesBack;
import com.newsreader.thenewsreader.parceler.GsonParceler;
import com.newsreader.thenewsreader.services.DaggerService;
import com.newsreader.thenewsreader.utils.Utils;
import com.newsreader.thenewsreader.viewpresenter.SourceScreen;
import com.newsreader.thenewsreader.viewpresenter.SplashScreen;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import flow.FlowDelegate;
import flow.History;
import flow.path.Path;
import flow.path.PathContainerView;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class BaseActivity extends Activity implements Flow.Dispatcher, ActivityFinisher {

    private MortarScope mortarScope;

    private FlowDelegate flowDelegate;

    @BindView(R.id.container)
    PathContainerView pathContainerView;

    @Override
    public Object getSystemService(String name) {
        // see: https://github.com/square/mortar/issues/155
        if (getApplication() == null) {
            return super.getSystemService(name);
        }

        Object service = null;
        if (flowDelegate != null) {
            service = flowDelegate.getSystemService(name);
        }

        if (service == null && mortarScope != null && mortarScope.hasService(name)) {
            service = mortarScope.getService(name);
        }

        return service != null ? service : super.getSystemService(name);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mortarScope = MortarScope.findChild(getApplicationContext(), getClass().getName());

        if (mortarScope == null) {
            Component component = DaggerBaseActivity_Component.builder()
                    .component(DaggerService.<TheNewsReader.Component>getDaggerComponent(getApplicationContext()))
                    .build();

            mortarScope = MortarScope.buildChild(getApplicationContext())
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(getClass().getName());
        }

        DaggerService.<Component>getDaggerComponent(this).inject(this);

        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            // original code, works on Lollipop SDKs
            // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // window.setStatusBarColor(getResources().getColor(YOUR_COLOR));

            try {
                // to work on old SDKs
                int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = 0x80000000;
                window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                Class<?> cls = window.getClass();
                Method method = cls.getDeclaredMethod("setStatusBarColor",
                        Integer.TYPE);

                method.invoke(window, getResources().getColor(R.color.hirwa));

            } catch (Exception e) {
                // upgrade your SDK and ADT :D
            }

        }



        GsonParceler parceler = new GsonParceler(new Gson());
        @SuppressWarnings("deprecation") FlowDelegate.NonConfigurationInstance nonConfig =
                (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.APP_NAME, 0);
        boolean isfirstLaunch = sharedPreferences.getBoolean(Utils.FIRST_LAUNCH, true);
        if (isfirstLaunch) {
            flowDelegate = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, History.single(new SplashScreen()), this);
        } else {
            flowDelegate = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, History.single(new SourceScreen()), this);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        flowDelegate.onPause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
        flowDelegate.onSaveInstanceState(outState);
    }

    @SuppressWarnings("deprecation") // https://code.google.com/p/android/issues/detail?id=151346
    @Override
    public Object onRetainNonConfigurationInstance() {
        return flowDelegate.onRetainNonConfigurationInstance();
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getClass().getName());
            if (activityScope != null) {
                activityScope.destroy();
            }
        }

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (((HandlesBack) pathContainerView).onBackPressed()) return;
        if (flowDelegate.onBackPressed()) return;
        super.onBackPressed();

    }

    // Flow.Dispatcher

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        Path path = traversal.destination.top();
        setTitle(path.getClass().getSimpleName());

        pathContainerView.dispatch(traversal, () -> {
            invalidateOptionsMenu();
            callback.onTraversalCompleted();
        });
    }

    @Override
    public void finsihActivity() {
        this.finish();
    }

    @dagger.Component(dependencies = TheNewsReader.Component.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(BaseActivity activity);
    }
}
