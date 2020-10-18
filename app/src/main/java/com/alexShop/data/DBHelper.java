package com.alexShop.data;

import android.content.Context;
import androidx.room.Room;
import com.alexShop.data.model.User;
import com.alexShop.ui.shop.StoreItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {

    private final Context context;
    private String DB_NAME = "database-name";

    public DBHelper(Context context) {
        this.context = context;
    }

    public User[] loadAllUsers() {
        final ApplicationDB userDB = getApplicationDB();
        final User[][] result = {null};
        Thread getAll = new Thread(new Runnable() {
            @Override
            public void run() {
                result[0] = userDB.userDao().loadAllUsers();
            }
        });
        getAll.start();
        return result[0];
    }

    @NotNull
    private ApplicationDB getApplicationDB() {
        return Room.databaseBuilder(context, ApplicationDB.class, DB_NAME).fallbackToDestructiveMigration().build();
    }

    public void insertUsers(final User newUser) {
        final ApplicationDB db = getApplicationDB();
        Thread insert = new Thread(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertUsers(newUser);
            }
        });
        insert.start();
    }

    public ArrayList<StoreItem> getAvailableItems() {
        final ApplicationDB storeItemDB = getApplicationDB();
        final StoreItem[][] storeItems = {null};
        Thread insert = new Thread(new Runnable() {
            @Override
            public void run() {
                storeItems[0] = storeItemDB.storeItemDao().getAvailable();
            }
        });
        insert.start();

        if (storeItems[0] == null) {
            return null;
        }

        return new ArrayList<>(Arrays.asList(storeItems[0]));
    }

    public ArrayList<StoreItem> getAllItems() {
        final ApplicationDB storeItemDB = getApplicationDB();
        final StoreItem[][] storeItems = {null};
        Thread insert = new Thread(new Runnable() {
            @Override
            public void run() {
                storeItems[0] = storeItemDB.storeItemDao().loadAllItems();
            }
        });
        insert.start();

        if (storeItems[0] == null) {
            return null;
        }

        return new ArrayList<>(Arrays.asList(storeItems[0]));
    }

    public void reloadFromFile() {
        final ApplicationDB storeItemDB = getApplicationDB();
        for (final StoreItem each : FakeDataBase.getData()) {
            Logger.getAnonymousLogger().log(Level.INFO, "LOADING " + each.name);
            Thread insert = new Thread(new Runnable() {
                @Override
                public void run() {

                    storeItemDB.storeItemDao().insertItems(each);
                }
            });

            insert.start();
        }

        Logger.getAnonymousLogger().log(Level.INFO, "DATA BASE SHOULD BE LOADED...");
    }

    public void addItem(final StoreItem storeItem) {
        final ApplicationDB storeItemDB = getApplicationDB();
        Thread insert = new Thread(new Runnable() {
            @Override
            public void run() {
                storeItemDB.storeItemDao().insertItems(storeItem);
            }
        });
        insert.start();
    }

    public void setItemPurchased(final String itemName) {
        final ApplicationDB storeItemDB = getApplicationDB();
        Thread setPurchased = new Thread(new Runnable() {
            @Override
            public void run() {
                storeItemDB.storeItemDao().setItemPurchased(itemName);
            }
        });

        setPurchased.start();
    }
}
