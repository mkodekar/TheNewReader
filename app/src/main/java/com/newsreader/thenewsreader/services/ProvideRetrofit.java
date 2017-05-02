package com.newsreader.thenewsreader.services;

import com.newsreader.thenewsreader.annotations.DaggerScope;
import com.newsreader.thenewsreader.application.TheNewsReader;
import com.newsreader.thenewsreader.utils.Utils;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rkodekar on 4/30/17.
 */

@DaggerScope(TheNewsReader.Component.class)
public class ProvideRetrofit {

    private RetrofitService retrofitService;

    @Inject
    public ProvideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }
}
