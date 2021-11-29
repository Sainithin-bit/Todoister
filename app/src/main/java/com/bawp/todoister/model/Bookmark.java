package com.bawp.todoister.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark_table")
public class Bookmark {

    @ColumnInfo(name="bookmarkid")
    @PrimaryKey(autoGenerate = true)
    public long bookmarkId;



    public String name;

    public String url;

    public Bookmark(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public long getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkId=" + bookmarkId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
