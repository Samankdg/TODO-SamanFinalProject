package com.example.todosaman.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todosaman.Tables.TODO;
import com.example.todosaman.Repository.TODORepository;

import java.util.List;

public class TODOViewModel extends AndroidViewModel {
    private TODORepository repository;
    private LiveData<List<TODO>> allTasks;

    public TODOViewModel(@NonNull Application application) {
        super(application);
        repository = new TODORepository(application);
        allTasks = repository.getAllTasks();
    }

    public void insert(TODO TODO) {
        repository.insert(TODO);
    }
    public void update(TODO TODO) {
        repository.update(TODO);
    }
    public void delete(TODO TODO){
        repository.delete(TODO);
    }
    public void deleteAllTasks() {
        repository.deleteAllTasks();
    }
    public LiveData<List<TODO>> getAllTasks(){
        return allTasks;
    }
}
