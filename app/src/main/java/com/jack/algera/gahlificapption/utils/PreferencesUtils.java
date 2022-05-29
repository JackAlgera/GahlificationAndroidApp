package com.jack.algera.gahlificapption.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jack.algera.gahlificapption.login.models.UserLogin;

public class PreferencesUtils {

    public static final String AUTH_SHARED_PREFERENCES = "AUTH_SHARED_PREFERENCES";
    public static final String TOKEN = "token";
    public static final String TIMESTAMP = "timestamp";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PreferencesUtils(Context context) {
        this.sharedPreferences = context.getSharedPreferences(AUTH_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void setToken(UserLogin userLogin) {
        editor.putString(TOKEN, userLogin.getToken());
        editor.putLong(TIMESTAMP, userLogin.getTimestamp());
        editor.commit();
    }

    public boolean isLoggedIn() {
        String token = sharedPreferences.getString(TOKEN, "");
        long timestamp = sharedPreferences.getLong(TIMESTAMP, -1);

        if (!token.isEmpty() && timestamp > 0L) {
            UserLogin login = new UserLogin(token, timestamp);
            return login.isTokenValid();
        }

        return false;
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, "");
    }
}
