package com.alexShop.data.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.alexShop.data.Converters;
import com.alexShop.data.model.StoreItemDao;
import com.alexShop.ui.shop.StoreItem;

@Database(entities = {StoreItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class StoreItemDB extends RoomDatabase {
    public abstract StoreItemDao storeItemDao();
}