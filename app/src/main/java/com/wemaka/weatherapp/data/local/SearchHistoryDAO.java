package com.wemaka.weatherapp.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.wemaka.weatherapp.data.model.SearchHistoryItem;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryDAO {
    public static final String TAG = "SearchHistoryDAO";
    private final SearchHistoryDatabaseHelper dbHelper;

    public SearchHistoryDAO(SearchHistoryDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertSearchQuery(String query) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SearchHistoryDatabaseHelper.COLUMN_QUERY, query);
        db.insert(SearchHistoryDatabaseHelper.TABLE_NAME, null, values);
    }

    public List<SearchHistoryItem> getAllSearchHistory() {
        List<SearchHistoryItem> history = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                SearchHistoryDatabaseHelper.TABLE_NAME,
                new String[]{SearchHistoryDatabaseHelper.COLUMN_ID, SearchHistoryDatabaseHelper.COLUMN_QUERY, SearchHistoryDatabaseHelper.COLUMN_TIMESTAMP},
                null, null, null, null,
                SearchHistoryDatabaseHelper.COLUMN_TIMESTAMP + " DESC"
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String query = cursor.getString(1);
            long timestamp = cursor.getLong(2);
            history.add(new SearchHistoryItem(id, query, timestamp));
        }
        cursor.close();
        return history;
    }

    public void deleteSearchHistory(String query) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                SearchHistoryDatabaseHelper.TABLE_NAME,
                SearchHistoryDatabaseHelper.COLUMN_QUERY + " = ?",
                new String[]{query}
        );
    }

    public void deleteAllHistory() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(SearchHistoryDatabaseHelper.TABLE_NAME, null, null);
    }

    public boolean isQueryExists(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("history", null, "query = ?", new String[]{query}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}