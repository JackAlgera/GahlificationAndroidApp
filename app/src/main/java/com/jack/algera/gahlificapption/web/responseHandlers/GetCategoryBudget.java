package com.jack.algera.gahlificapption.web.responseHandlers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.jack.algera.gahlificapption.budget.BudgetActivity;
import com.jack.algera.gahlificapption.budget.models.BudgetEntry;
import com.jack.algera.gahlificapption.budget.models.CategoryBudget;
import com.jack.algera.gahlificapption.fragments.BudgetEntryFragment;
import com.jack.algera.gahlificapption.web.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCategoryBudget implements Callback<CategoryBudget> {

    private final String sheetName;
    private final String category;
    private final LinearLayout linearLayout;
    private final TextView titleTotalCostTextView;
    private final FragmentManager fragmentManager;
    private final Context context;

    public GetCategoryBudget(String sheetName, String category, LinearLayout linearLayout, TextView titleTotalCostTextView, FragmentManager fragmentManager, Context context) {
        this.sheetName = sheetName;
        this.category = category;
        this.linearLayout = linearLayout;
        this.titleTotalCostTextView = titleTotalCostTextView;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public void start() {
        Call<CategoryBudget> call = RetrofitClient.getInstance(context)
                .getBudgetApi()
                .getCategoryBudget(sheetName, category);
        call.enqueue(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(Call<CategoryBudget> call, Response<CategoryBudget> response) {
        if (response.isSuccessful()) {
            CategoryBudget categoryBudget = response.body();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setReorderingAllowed(true);

            fragmentManager.getFragments().forEach(fragment -> {
                if (fragment != null && fragment.getTag().equals("budgetEntry")) {
                    fragmentTransaction.remove(fragment);
                }
            });

            for (BudgetEntry budgetEntry : categoryBudget.getEntries()) {
                fragmentTransaction.add(linearLayout.getId(), BudgetEntryFragment.newInstance(budgetEntry), "budgetEntry");
            }
            fragmentTransaction.commit();

            titleTotalCostTextView.setText(String.format("%s/%s", categoryBudget.getTotalSpent(), categoryBudget.getBudget()));
        } else {
            System.out.println("GetCategoryBudget didn't work");
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<CategoryBudget> call, Throwable t) {
        t.printStackTrace();
    }
}
