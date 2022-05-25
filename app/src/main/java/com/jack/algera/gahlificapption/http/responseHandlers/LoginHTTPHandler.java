package com.jack.algera.gahlificapption.http.responseHandlers;

import android.content.Context;
import android.widget.Toast;

import com.jack.algera.gahlificapption.authentication.UserLogin;
import com.jack.algera.gahlificapption.budget.models.LoginRequest;
import com.jack.algera.gahlificapption.http.RetrofitClient;
import com.jack.algera.gahlificapption.utils.OnAsyncTaskCompleted;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHTTPHandler implements Callback<UserLogin> {

    private final Context context;
    private final OnAsyncTaskCompleted listener;
    private final String username;
    private final String password;

    public LoginHTTPHandler(Context context, OnAsyncTaskCompleted listener, String username, String password) {
        this.context = context;
        this.listener = listener;
        this.username = username;
        this.password = password;
    }

    public void start() {
        Toast.makeText(context, "Trying to login...", Toast.LENGTH_SHORT).show();

        Call<UserLogin> call = RetrofitClient.getInstance(context)
                .getBudgetApi()
                .login(new LoginRequest(username, password));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
        if (response.isSuccessful()) {
            switch (response.code()) {
                case 200:
                    Toast.makeText(context, "You're logged in ! :D", Toast.LENGTH_SHORT).show();
                    new PreferencesUtils(context).setToken(response.body());
                    listener.onTaskCompleted();
                    break;
                case 401:
                    Toast.makeText(context, "Incorrect username or password love :/", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("LoginHTTPHandler didn't work");
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<UserLogin> call, Throwable t) {
        Toast.makeText(context, "Woops something went wrong... :(", Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
}