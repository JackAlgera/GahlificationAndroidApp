package com.jack.algera.gahlificapption.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jack.algera.gahlificapption.MainActivity;
import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.http.responseHandlers.LoginHTTPHandler;
import com.jack.algera.gahlificapption.utils.OnAsyncTaskCompleted;

public class LoginActivity extends AppCompatActivity implements OnAsyncTaskCompleted {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameTextEdit = findViewById(R.id.username);
        EditText passwordTextEdit = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> {
            String username = usernameTextEdit.getText().toString();
            String password = passwordTextEdit.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please provide a username and password !", Toast.LENGTH_SHORT).show();
            } else {
                LoginHTTPHandler loginHTTPHandler = new LoginHTTPHandler(getApplicationContext(), this, username, password);
                loginHTTPHandler.start();
            }
        });
    }

    @Override
    public void onTaskCompleted() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
