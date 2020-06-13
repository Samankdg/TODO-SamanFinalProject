package com.example.todosaman.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todosaman.Database.Tables.TODO;

import java.util.List;

@Dao
public interface TODODao {
    @Insert
    void insert(TODO TODO);

    @Update
    void update(TODO TODO);

    @Delete
    void delete(TODO TODO);

    @Query("DELETE FROM task_table")
    void deleteAllNotes();

    @Query("SELECT * FROM task_table ORDER BY priority DESC")
    LiveData<List<TODO>> getAllNotes();
}
