package com.example.todosaman.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todosaman.Tables.User;
import com.example.todosaman.Tables.UserTODO;


@Dao
public interface UserDao {
    @Query("SELECT * FROM User where email= :mail and password= :password")
    User getUser(String mail, String password);

    @Insert
    void insert(User User);

    @Update
    void update(User User);

    @Delete
    void delete(User User);

    @Query("Select * from User")
    public UserTODO getData();
}

