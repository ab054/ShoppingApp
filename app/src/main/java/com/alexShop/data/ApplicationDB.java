package com.alexShop.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.alexShop.data.daos.StoreItemDao;
import com.alexShop.data.daos.UserDao;
import com.alexShop.data.model.User;
import com.alexShop.ui.shop.StoreItem;

@Database(entities = {StoreItem.class, User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ApplicationDB extends RoomDatabase {
    public abstract StoreItemDao storeItemDao();

    public abstract UserDao userDao();
}