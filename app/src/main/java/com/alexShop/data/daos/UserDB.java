package com.alexShop.data.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.alexShop.data.model.User;
import com.alexShop.data.model.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao userDao();
}