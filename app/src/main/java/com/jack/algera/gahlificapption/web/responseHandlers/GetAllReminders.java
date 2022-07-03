package com.jack.algera.gahlificapption.web.responseHandlers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.jack.algera.gahlificapption.budget.models.BudgetEntry;
import com.jack.algera.gahlificapption.fragments.BudgetEntryFragment;
import com.jack.algera.gahlificapption.reminders.ReminderEntryFragment;
import com.jack.algera.gahlificapption.reminders.models.Reminder;
import com.jack.algera.gahlificapption.web.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllReminders implements Callback<List<Reminder>> {

    private final LinearLayout linearLayout;
    private final FragmentManager fragmentManager;
    private final Context context;

    public GetAllReminders(LinearLayout linearLayout, FragmentManager fragmentManager, Context context) {
        this.linearLayout = linearLayout;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public void start() {
        Call<List<Reminder>> call = RetrofitClient.getInstance(context)
                .getCachedBudgetApi()
                .getReminders();
        call.enqueue(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(Call<List<Reminder>> call, Response<List<Reminder>> response) {
        if (response.isSuccessful()) {
            List<Reminder> reminderList = response.body();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setReorderingAllowed(true);

            for (Reminder reminder : reminderList) {
                fragmentTransaction.add(linearLayout.getId(), ReminderEntryFragment.newInstance(reminder), "reminderEntry");
            }
            fragmentTransaction.commit();
        } else {
            System.out.println("GetAllReminders didn't work");
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Reminder>> call, Throwable t) {
        t.printStackTrace();
    }
}
