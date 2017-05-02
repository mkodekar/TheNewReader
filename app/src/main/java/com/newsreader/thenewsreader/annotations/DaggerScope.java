package com.newsreader.thenewsreader.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by rkodekar on 4/30/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DaggerScope {
    Class<?> value();
}
