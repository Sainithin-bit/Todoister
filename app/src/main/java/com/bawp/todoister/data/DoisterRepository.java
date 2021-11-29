package com.bawp.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.todoister.model.Bookmark;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.utils.TaskRoomDatabase;

import java.util.List;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final BookmarkDao bookmarkDao;
    private final LiveData<List<Task>> alltasks;
    private final LiveData<List<Bookmark>> allbookmarks;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database=TaskRoomDatabase.getDatabase(application);
        taskDao = database.taskDao();
        bookmarkDao=database.bookmarkDao();
        alltasks = taskDao.getTasks();
        allbookmarks=bookmarkDao.getBookmarks();
    }

    public LiveData<List<Task>> getAlltasks(){
        return alltasks;
    }

    public LiveData<List<Bookmark>> getAllbookmarks(){
        return allbookmarks;
    }

    public void insert(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->taskDao.insertTask(task));

    }
    public void insert(Bookmark bookmark){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->bookmarkDao.insertBookmark(bookmark));

    }

    public LiveData<Task> get(long id){
        return taskDao.get(id);
    }
    public LiveData<Bookmark> getBookmark(long id){ return bookmarkDao.getBookmark(id); }

    public void update(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->taskDao.update(task));
    }
    public void update(Bookmark bookmark){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->bookmarkDao.update(bookmark));
    }

    public  void delete(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->taskDao.delete(task));
    }
    public  void delete(Bookmark bookmark){
        TaskRoomDatabase.databaseWriterExecutor.execute(()->bookmarkDao.delete(bookmark));
    }

}
