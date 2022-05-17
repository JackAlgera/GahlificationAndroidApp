package com.jack.algera.gahlificapption.http.responseHandlers;

import android.content.Context;
import android.widget.Toast;

import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryRequest;
import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryResponse;
import com.jack.algera.gahlificapption.http.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryValue implements Callback<AddCategoryEntryResponse> {

    private final Context context;
    private final String sheetName;
    private final String category;
    private final AddCategoryEntryRequest body;

    public AddCategoryValue(Context context, String sheetName, String category, AddCategoryEntryRequest body) {
        this.context = context;
        this.sheetName = sheetName;
        this.category = category;
        this.body = body;
    }

    public void start() {
        Toast.makeText(context, "Adding cost...", Toast.LENGTH_SHORT).show();

        Call<AddCategoryEntryResponse> call = RetrofitClient.getInstance(context)
                .getBudgetApi()
                .addCategoryEntry(sheetName, category, body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AddCategoryEntryResponse> call, Response<AddCategoryEntryResponse> response) {
        if (response.isSuccessful()) {
            Toast.makeText(context, String.format("Added %s to %s ! :D", body.getCost(), category), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "On no, it failed... :(", Toast.LENGTH_SHORT).show();
            System.out.println("AddCategoryValue didn't work");
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<AddCategoryEntryResponse> call, Throwable t) {
        t.printStackTrace();
        Toast.makeText(context, "On no, it failed... what? :(", Toast.LENGTH_SHORT).show();
    }
}