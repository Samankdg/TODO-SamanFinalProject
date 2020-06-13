package com.example.todosaman.Database.Tables;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserTODO {
    @Embedded
    User user;

    @Relation(parentColumn = "id", entityColumn = "id", entity = TODO.class)
    public List<TODO> userTODO;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TODO> getUserTODO() {
        return userTODO;
    }

    public void setUserTODO(List<TODO> userTODO) {
        this.userTODO = userTODO;
    }
}
