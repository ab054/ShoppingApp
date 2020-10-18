package com.alexShop.data;

import android.content.Context;
import androidx.room.Room;
import com.alexShop.data.daos.UserDB;
import com.alexShop.data.model.User;
import org.jetbrains.annotations.NotNull;

public class DBHelper {

    private final Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    public User[] loadAllUsers() {
        final UserDB userDB = getUserDao();
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
    private UserDB getUserDao() {
        return Room.databaseBuilder(context, UserDB.class, "database-name").build();
    }

    public void insertUsers(final User newUser) {
        final UserDB userDB = getUserDao();
        Thread insert = new Thread(new Runnable() {
            @Override
            public void run() {
                userDB.userDao().insertUsers(newUser);
            }
        });
        insert.start();
    }
}
