package com.newsreader.thenewsreader.mvp;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by rkodekar on 4/30/17.
 */

public class Presenter implements PresenterInterface {

    private CompositeSubscription compositeSubscription;

    @Override
    public void Load() {
        configureSubscription();

    }

    @Override
    public void dropView() {
        unSubscribeAll();
    }

    private CompositeSubscription configureSubscription() {
        if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }

        return compositeSubscription;
    }


    public void unSubscribeAll() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            compositeSubscription.clear();
            compositeSubscription = null;
        }
    }

    public <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        Subscription subscription = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(observer);
        configureSubscription().add(subscription);
    }

}
