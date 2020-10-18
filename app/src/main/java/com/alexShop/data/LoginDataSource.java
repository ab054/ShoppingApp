package com.alexShop.data;

import android.content.Context;
import com.alexShop.data.model.LoggedInUser;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final String LOGIN_URL = "http://10.0.2.2:5005/login";
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private Boolean loginSuccessful;
    private LoggedInUser user;

    public Result<LoggedInUser> login(String username, String password, Context context) {
        try {
            loginRequest(username, password, context);
            user = null;
            if (loginSuccessful) {
                user = new LoggedInUser(java.util.UUID.randomUUID().toString(), username);
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new Exception("CHECK CREDS"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    private void loginRequest(String username, String password, Context context) throws JSONException {
        String requestBody = "{\"login\":\"" + username + "\",\"password\":\"" + password + "\"}";
        JSONObject jsonBody = new JSONObject(requestBody);
        JsonObjectRequest request = new JsonObjectRequest(LOGIN_URL, jsonBody, getListener(), getErrorListener());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private Response.Listener<JSONObject> getListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LOGGER.log(Level.INFO, "\n" + response.toString(3));
                    loginSuccessful = (Boolean) response.get("success");
                } catch (JSONException e) {
                    LOGGER.log(Level.WARNING, e.getStackTrace().toString());
                }
            }
        };
    }

    @NotNull
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    public void logout() {
        loginSuccessful = false;
        user = null;
    }
}