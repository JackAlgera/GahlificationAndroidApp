package com.jack.algera.gahlificapption.http;

import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryRequest;
import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryResponse;
import com.jack.algera.gahlificapption.budget.models.LoginRequest;
import com.jack.algera.gahlificapption.budget.models.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BudgetAPI {

    @GET("categories")
    Call<List<String>> listCategories();

    @GET("sheets")
    Call<List<String>> listSheets();

    @POST("sheets/{sheetName}/categories/{category}")
    Call<AddCategoryEntryResponse> addCategoryEntry(@Path("sheetName") String sheetName, @Path("category") String category, @Body AddCategoryEntryRequest request);

    @POST("authenticate")
    Call<LoginResponse> login(@Body LoginRequest request);
}
