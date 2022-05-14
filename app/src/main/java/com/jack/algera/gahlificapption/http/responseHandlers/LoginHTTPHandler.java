package com.jack.algera.gahlificapption.http.responseHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jack.algera.gahlificapption.http.BackendAPIClient;
import com.jack.algera.gahlificapption.utils.OnAsyncTaskCompleted;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import java.io.IOException;

public class LoginHTTPHandler extends AsyncTask<Void, Void, Integer> {

    private final Context context;
    private final PreferencesUtils preferencesUtils;
    private final OnAsyncTaskCompleted listener;
    private final String username;
    private final String password;

    public LoginHTTPHandler(Context context, PreferencesUtils preferencesUtils, OnAsyncTaskCompleted listener, String username, String password) {
        this.context = context;
        this.preferencesUtils = preferencesUtils;
        this.listener = listener;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Toast.makeText(context, "Trying to login...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            return BackendAPIClient.login(preferencesUtils, username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        super.onPostExecute(responseCode);

        switch (responseCode) {
            case 200:
                Toast.makeText(context, "You're logged in ! :D", Toast.LENGTH_SHORT).show();
                listener.onTaskCompleted();
                break;
            case 401:
                Toast.makeText(context, "Incorrect username or password love :/", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "Woops something went wrong... :(", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}