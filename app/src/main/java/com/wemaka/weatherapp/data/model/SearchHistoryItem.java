package com.wemaka.weatherapp.data.model;

public class SearchHistoryItem {
    private long id;
    private String query;
    private long timestamp;

    public SearchHistoryItem(long id, String query, long timestamp) {
        this.id = id;
        this.query = query;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public String getQuery() { return query; }
    public long getTimestamp() { return timestamp; }

    public void setId(long id) { this.id = id; }
    public void setQuery(String query) { this.query = query; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
