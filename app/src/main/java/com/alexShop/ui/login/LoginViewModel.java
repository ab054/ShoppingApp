package com.alexShop.ui.login;

import android.content.Context;
import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.alexShop.R;
import com.alexShop.data.LoginRepository;
import com.alexShop.data.Result;
import com.alexShop.data.model.LoggedInUser;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewModel extends ViewModel {

    private static final String LOGIN_URL = "http://10.0.2.2:5005/login";
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private Context context;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void logout() {
        loginRepository.logout();
    }

    public void login(String username, String password, Context applicationContext) {
        loginRepository.setContext(applicationContext);
        this.context = applicationContext;
        makeLoginRequest(username, password, context);
    }

    public void makeLoginRequest(final String username, String password, Context context) {
        JSONObject jsonBody = getJsonBody(username, password);

        JsonObjectRequest request = new JsonObjectRequest(LOGIN_URL, jsonBody,

            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Result<LoggedInUser> userResult;
                    LoggedInUser loggedInUser;

                    try {
                        LOGGER.log(Level.INFO, "\n" + response.toString(3));
                        Boolean loginSuccessful = (Boolean) response.get("success");

                        if (loginSuccessful) {
                            loggedInUser = new LoggedInUser(UUID.randomUUID().toString(), username);
                            userResult = new Result.Success<>(loggedInUser);
                        } else {
                            userResult = new Result.Error(new Exception("CHECK CREDS"));
                        }

                        if (userResult instanceof Result.Success) {
                            LoggedInUser data = ((Result.Success<LoggedInUser>) userResult).getData();
                            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                            loginRepository.setLoggedInUser(((Result.Success<LoggedInUser>) userResult).getData());

                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    } catch (JSONException e) {
                        LOGGER.log(Level.WARNING, e.getStackTrace().toString());
                    }
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    @Nullable
    private JSONObject getJsonBody(String username, String password) {
        String requestBody = "{\"login\":\"" + username + "\",\"password\":\"" + password + "\"}";
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject(requestBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody;
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}