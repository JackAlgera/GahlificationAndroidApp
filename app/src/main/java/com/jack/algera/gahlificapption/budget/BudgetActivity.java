package com.jack.algera.gahlificapption.budget;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.http.BudgetAPI;
import com.jack.algera.gahlificapption.http.responseHandlers.AddCategoryValue;
import com.jack.algera.gahlificapption.http.responseHandlers.GetAllCategories;
import com.jack.algera.gahlificapption.http.responseHandlers.GetAllSheetNames;
import com.jack.algera.gahlificapption.utils.PreferencesUtils;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class BudgetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        PreferencesUtils preferencesUtils = new PreferencesUtils(getApplicationContext());

        Spinner selectSheetNames = (Spinner) findViewById(R.id.selectSheetName);
        Spinner selectCategories = (Spinner) findViewById(R.id.selectCategories);

        GetAllSheetNames getAllSheetNames = new GetAllSheetNames(this, preferencesUtils, selectSheetNames);
        getAllSheetNames.start();

        GetAllCategories getAllCategories = new GetAllCategories(this, selectCategories, preferencesUtils);
        getAllCategories.start();

        EditText entryDescription = (EditText) findViewById(R.id.entryDescription);
        EditText entryCost = (EditText) findViewById(R.id.entryCost);
        Button sendRequestButton = (Button) findViewById(R.id.send_request_button);

        sendRequestButton.setOnClickListener(view -> {
            String sheetName = selectSheetNames.getSelectedItem().toString();
            String category = selectCategories.getSelectedItem().toString();
            String cost = entryCost.getText().toString();
            String description = entryDescription.getText().toString();

            AddCategoryValue addCategoryValue = new AddCategoryValue(getApplicationContext(), preferencesUtils, sheetName, category, cost, description);
            addCategoryValue.execute();
        });
    }
}