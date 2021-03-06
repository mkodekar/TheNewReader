package com.newsreader.thenewsreader.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by rkodekar on 4/30/17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface Layout {
    int value();
}
