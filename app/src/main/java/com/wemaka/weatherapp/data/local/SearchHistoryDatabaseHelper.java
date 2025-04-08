package com.wemaka.weatherapp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearchHistoryDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "SearchHistoryDatabaseHelper";
    public static final String DATABASE_NAME = "search_history.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUERY = "query";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public SearchHistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUERY + " TEXT NOT NULL, " +
                COLUMN_TIMESTAMP + " INTEGER DEFAULT (strftime('%s','now') * 1000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}