package com.alexShop.data;

import android.content.Context;
import com.alexShop.data.model.LoggedInUser;
import com.alexShop.data.model.User;

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
    private DBHelper dbHelper;

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
        addUserToDB(user);
        getAllUsers();
    }

    private User[] getAllUsers() {
        return dbHelper.loadAllUsers();
    }

    private void addUserToDB(LoggedInUser user) {
        dbHelper = new DBHelper(context);
        final User newUser = new User(user.getUserId(), user.getDisplayName());
        dbHelper.insertUsers(newUser);
    }

    public Result<LoggedInUser> login(String username, String password, Context context) {
        Result<LoggedInUser> result = dataSource.login(username, password, context);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public void setContext(Context applicationContext) {
        this.context = applicationContext;
    }
}