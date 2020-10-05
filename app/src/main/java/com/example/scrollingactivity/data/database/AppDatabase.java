package com.example.scrollingactivity.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.scrollingactivity.data.model.User;
import com.example.scrollingactivity.data.model.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}