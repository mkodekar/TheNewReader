package com.newsreader.thenewsreader.appfactory;

/**
 * Created by rkodekar on 4/30/17.
 */

public interface ScreenComponentFactory<T> {
    Object createComponent(T parent);
}
