package com.jack.algera.gahlificapption.http;

import org.apache.http.HttpHeaders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BudgetAPI {

    @GET("categories")
    Call<List<String>> listCategories(@Header(HttpHeaders.AUTHORIZATION) String authorization);

    @GET("sheets")
    Call<List<String>> listSheets(@Header(HttpHeaders.AUTHORIZATION) String authorization);
}
