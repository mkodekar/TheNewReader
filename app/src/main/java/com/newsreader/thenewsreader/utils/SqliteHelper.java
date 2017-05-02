package com.newsreader.thenewsreader.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rkodekar on 4/30/17.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "thenewreader.sqlite";
    private static final String TABLE_SOURCE = "sources";
    private static final String TABLE_ARTICLES = "articles";

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
