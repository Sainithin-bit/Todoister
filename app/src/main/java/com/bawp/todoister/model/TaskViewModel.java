package com.bawp.todoister.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.todoister.data.DoisterRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static DoisterRepository repository;
    public final LiveData<List<Task>> allTasks;
    public final LiveData<List<Bookmark>> allBookmarks;

    public TaskViewModel(Application application) {
        super(application);
        repository=new DoisterRepository(application);
        allTasks=repository.getAlltasks();
        allBookmarks=repository.getAllbookmarks();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
    public static void insert(Task task){repository.insert(task);}
    public static LiveData<Task> get(long id){return repository.get(id);}
    public  static void update(Task task){repository.update(task);}
    public  static void delete(Task task){repository.delete(task);}
    public LiveData<List<Bookmark>> getAllBookmarks(){ return allBookmarks; }
    public static void insert(Bookmark bookmark){repository.insert(bookmark);}
    public static LiveData<Bookmark> getbookmark(long id){return repository.getBookmark(id);}
    public  static void update(Bookmark bookmark){repository.update(bookmark);}
    public  static void delete(Bookmark bookmark){repository.delete(bookmark);}

}
