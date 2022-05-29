package com.jack.algera.gahlificapption.web;

import com.jack.algera.gahlificapption.budget.models.CategoryBudget;
import com.jack.algera.gahlificapption.login.models.UserLogin;
import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryRequest;
import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryResponse;
import com.jack.algera.gahlificapption.budget.models.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BudgetAPI {

    @GET("sheets/names")
    Call<List<String>> listSheets();

    @GET("categories/types")
    Call<List<String>> listCategories();

    @POST("sheets/{sheetName}/categories/{category}")
    Call<AddCategoryEntryResponse> addCategoryEntry(@Path("sheetName") String sheetName, @Path("category") String category, @Body AddCategoryEntryRequest request);

    @GET("sheets/{sheetName}/categories/{category}/budget")
    Call<CategoryBudget> getCategoryBudget(@Path("sheetName") String sheetName, @Path("category") String category);

    @POST("authenticate")
    Call<UserLogin> login(@Body LoginRequest request);
}
