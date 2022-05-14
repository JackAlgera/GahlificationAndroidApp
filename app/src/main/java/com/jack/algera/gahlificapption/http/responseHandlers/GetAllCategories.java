package com.jack.algera.gahlificapption.http.responseHandlers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jack.algera.gahlificapption.http.RetrofitClient;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllCategories implements Callback<List<String>> {

    private final Spinner spinner;
    private final Context context;
    private final PreferencesUtils preferencesUtils;

    public GetAllCategories(Context context, Spinner spinner, PreferencesUtils preferencesUtils) {
        this.spinner = spinner;
        this.context = context;
        this.preferencesUtils = preferencesUtils;
    }

    public void start() {
        Call<List<String>> call = RetrofitClient.getInstance(context)
                .getCachedBudgetApi()
                .listCategories("Bearer " + preferencesUtils.getToken());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
        if (response.isSuccessful()) {
            List<String> catergoriesList = response.body();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, catergoriesList);
            spinner.setAdapter(adapter);
        } else {
            System.out.println("Shit didn't work");
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<String>> call, Throwable t) {
        t.printStackTrace();
    }
}
