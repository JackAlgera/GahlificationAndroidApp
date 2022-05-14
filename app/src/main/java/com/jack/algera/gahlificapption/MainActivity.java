package com.jack.algera.gahlificapption;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jack.algera.gahlificapption.authentication.LoginActivity;
import com.jack.algera.gahlificapption.budget.BudgetActivity;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferencesUtils preferencesUtils = new PreferencesUtils(getApplicationContext());

        if (!preferencesUtils.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        try {
            File httpCacheDir = new File(this.getCacheDir(), "http");
            long httpCacheSize = 30 * 1024 * 1024; // 30 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            System.out.println("HTTP response cache installation failed:\" + e");
        }

        Button gotoBudgetButton = findViewById(R.id.goto_budget_button);
        gotoBudgetButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, BudgetActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }
}