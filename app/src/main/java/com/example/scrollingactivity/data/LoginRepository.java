package com.example.scrollingactivity.data;

import android.content.Context;
import androidx.room.Room;
import com.example.scrollingactivity.data.database.AppDatabase;
import com.example.scrollingactivity.data.model.LoggedInUser;
import com.example.scrollingactivity.data.model.User;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;
    private Context context;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        recordToDB(user);
    }

    private void recordToDB(LoggedInUser user) {
        final AppDatabase db = Room.databaseBuilder(context,
            AppDatabase.class, "database-name").build();

        final User newUser = new User(user.getUserId(), user.getDisplayName());

        Thread insert = new Thread(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertUsers(newUser);
            }
        });
        insert.start();

    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public void setContext(Context applicationContext) {
        this.context = applicationContext;
    }
}