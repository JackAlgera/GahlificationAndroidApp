package com.jack.algera.gahlificapption.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://gahlification.herokuapp.com/api/";

    private static RetrofitClient instance;

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
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        retrofitCache = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient(context))
                .build();
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

    private OkHttpClient okHttpClient(Context context) {
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);

        return new OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(networkInterceptor())
                .build();
    }
}
