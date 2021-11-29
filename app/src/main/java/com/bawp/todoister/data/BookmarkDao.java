package com.bawp.todoister.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.todoister.model.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert
    void insertBookmark(Bookmark bookmark);

    @Query("Delete  from bookmark_table")
    void deleteAll();

    @Query("Select * from bookmark_table")
    LiveData<List<Bookmark>> getBookmarks();

    @Query("Select * from bookmark_table where bookmark_table.bookmarkid==:id")
    LiveData<Bookmark> getBookmark(long id);

    @Update
    void update(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);


}
