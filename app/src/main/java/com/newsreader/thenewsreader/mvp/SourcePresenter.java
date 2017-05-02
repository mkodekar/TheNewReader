package com.newsreader.thenewsreader.mvp;

import com.newsreader.thenewsreader.models.Sources;

import rx.Observer;


/**
 * Created by rkodekar on 4/30/17.
 */

public class SourcePresenter extends Presenter implements Observer<Sources> {

    private SourceInterface sourceInterface;

    public SourcePresenter(SourceInterface sourceInterface) {
        this.sourceInterface = sourceInterface;
    }

    @Override
    public void onCompleted() {
        sourceInterface.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        sourceInterface.onFetchError(e.getMessage());
    }

    @Override
    public void onNext(Sources sources) {
        sourceInterface.onSuccessfullyFetched(sources);
    }

    public void fetchSources() {
        unSubscribeAll();
        subscribe(sourceInterface.getSources(),SourcePresenter.this);
    }
}
