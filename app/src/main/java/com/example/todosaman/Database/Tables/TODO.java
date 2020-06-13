package com.example.todosaman.Database.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class TODO {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String title;
    private String description;
    private String date_button;
    private String time_button;
    private int priority;

    public TODO(String title, String description, String date_button, String time_button, int priority) {
        this.title = title;
        this.description = description;
        this.date_button = date_button;
        this.time_button = time_button;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_button() {
        return date_button;
    }

    public String getTime_button() {
        return time_button;
    }

    public int getPriority() {
        return priority;
    }
}
