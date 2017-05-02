package com.newsreader.thenewsreader.mvp;

import com.newsreader.thenewsreader.models.Sources;

import rx.Observable;

/**
 * Created by rkodekar on 4/30/17.
 */

public interface SourceInterface {
    void onCompleted();
    void onFetchError(String error);
    void onSuccessfullyFetched(Sources sources);
    Observable<Sources> getSources();

}
