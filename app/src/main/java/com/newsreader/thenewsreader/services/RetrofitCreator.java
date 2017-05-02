package com.newsreader.thenewsreader.services;

import com.newsreader.thenewsreader.utils.Utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rkodekar on 5/2/17.
 */

public class RetrofitCreator {

    public RetrofitCreator() {
    }

    public RetrofitService getRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(RetrofitService.class);
    }
}
