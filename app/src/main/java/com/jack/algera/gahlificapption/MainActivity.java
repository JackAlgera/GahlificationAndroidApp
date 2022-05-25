package com.jack.algera.gahlificapption;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.jack.algera.gahlificapption.authentication.LoginActivity;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        setupCache();

        PreferencesUtils preferencesUtils = new PreferencesUtils(getApplicationContext());

        if (!preferencesUtils.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }

    private void setupCache() {
        try {
            File httpCacheDir = new File(this.getCacheDir(), "http");
            long httpCacheSize = 30 * 1024 * 1024; // 30 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            System.out.println("HTTP response cache installation failed:\" + e");
        }
    }
}