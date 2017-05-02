package com.newsreader.thenewsreader.services;

import com.newsreader.thenewsreader.models.Articles;
import com.newsreader.thenewsreader.models.Sources;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by rkodekar on 4/30/17.
 */

public interface RetrofitService {

    @GET("sources")
    Observable<Sources> getSource();

    @GET("articles")
    Observable<Articles> getArticles(@Query("source") String source, @Query("apiKey") String apiKey);

}
