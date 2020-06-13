package com.example.todosaman.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todosaman.Database.Dao.TODODao;
import com.example.todosaman.Database.AppDatabase;
import com.example.todosaman.Tables.TODO;

import java.util.List;

public class TODORepository {
    private TODODao TODODao;
    private LiveData<List<TODO>> allTasks;

    public TODORepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        TODODao = database.taskDao();
        allTasks = TODODao.getAllNotes();
    }
    public void insert(TODO TODO){
        new InsertTaskAsyncTask(TODODao).execute(TODO);
    }
    public void update(TODO TODO){
        new UpdateTaskAsyncTask(TODODao).execute(TODO);
    }
    public void delete(TODO TODO){
        new DeleteTaskAsyncTask(TODODao).execute(TODO);
    }
    public void deleteAllTasks(){
        new DeleteAllTasksAsyncTask(TODODao).execute();
    }

    public LiveData<List<TODO>> getAllTasks() {
        return allTasks;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<TODO, Void, Void> {
        private com.example.todosaman.Database.Dao.TODODao TODODao;

        private InsertTaskAsyncTask(TODODao TODODao) {
            this.TODODao = TODODao;
        }
        @Override
        protected Void doInBackground(TODO... TODOS) {
            TODODao.insert(TODOS[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<TODO, Void, Void> {
        private TODODao TODODao;

        private UpdateTaskAsyncTask(TODODao TODODao) {
            this.TODODao = TODODao;
        }
        @Override
        protected Void doInBackground(TODO... TODOS) {
            TODODao.update(TODOS[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<TODO, Void, Void> {
        private TODODao TODODao;

        private DeleteTaskAsyncTask(TODODao TODODao) {
            this.TODODao = TODODao;
        }
        @Override
        protected Void doInBackground(TODO... TODOS) {
            TODODao.delete(TODOS[0]);
            return null;
        }
    }

    private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TODODao TODODao;

        private DeleteAllTasksAsyncTask(TODODao TODODao) {
            this.TODODao = TODODao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            TODODao.deleteAllNotes();
            return null;
        }
    }

}
