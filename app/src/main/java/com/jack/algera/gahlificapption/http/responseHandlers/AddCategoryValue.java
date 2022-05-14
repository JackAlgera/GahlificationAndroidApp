package com.jack.algera.gahlificapption.http.responseHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jack.algera.gahlificapption.http.BackendAPIClient;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import java.io.IOException;

public class AddCategoryValue extends AsyncTask<Void, Void, Integer> {

    private final Context context;
    private final PreferencesUtils preferencesUtils;
    private final String sheetName;
    private final String category;
    private final String cost;
    private final String description;

    public AddCategoryValue(Context context, PreferencesUtils preferencesUtils, String sheetName, String category, String cost, String description) {
        this.context = context;
        this.preferencesUtils = preferencesUtils;
        this.sheetName = sheetName;
        this.category = category;
        this.cost = cost;
        this.description = description;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Toast.makeText(context, "Adding cost...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            return BackendAPIClient.addCategoryValue(preferencesUtils, sheetName, category, cost, description);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        super.onPostExecute(responseCode);

        if (responseCode == 200) {
            Toast.makeText(context, String.format("Added %s to %s ! :D", cost, category), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "On no, it failed... :(", Toast.LENGTH_SHORT).show();
        }
    }
}