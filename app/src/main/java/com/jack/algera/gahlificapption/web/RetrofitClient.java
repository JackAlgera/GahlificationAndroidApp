package com.jack.algera.gahlificapption.web;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static final String BASE_URL = "https://gahlification.herokuapp.com/api/";
    private static final String BASE_URL = "http://localhost:8080/api/";

    private static RetrofitClient instance;
    private static PreferencesUtils preferencesUtils;

    private final Retrofit retrofit;
    private final Retrofit retrofitCache;

    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";
    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final int CACHE_VALIDITY_TIME = 30; // minutes

    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    private RetrofitClient(Context context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient().newBuilder()
                        .addInterceptor(tokenInterceptor())
                        .addInterceptor(loggingInterceptor)
                        .build())
                .build();
        retrofitCache = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient(context))
                .build();

        preferencesUtils = new PreferencesUtils(context.getApplicationContext());
    }

    public BudgetAPI getBudgetApi() {
        return retrofit.create(BudgetAPI.class);
    }

    public BudgetAPI getCachedBudgetApi() {
        return retrofitCache.create(BudgetAPI.class);
    }

    private Interceptor networkInterceptor() {
        return chain -> {
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(CACHE_VALIDITY_TIME, TimeUnit.MINUTES)
                    .build();

            Response response = chain.proceed(chain.request());

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    private Interceptor tokenInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request()
                        .newBuilder()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + preferencesUtils.getToken())
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    private OkHttpClient okHttpClient(Context context) {
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(tokenInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
