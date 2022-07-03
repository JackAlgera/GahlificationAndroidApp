package com.jack.algera.gahlificapption.budget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.budget.models.AddCategoryEntryRequest;
import com.jack.algera.gahlificapption.utils.OnAsyncTaskCompleted;
import com.jack.algera.gahlificapption.web.responseHandlers.AddCategoryValue;
import com.jack.algera.gahlificapption.web.responseHandlers.GetAllCategories;
import com.jack.algera.gahlificapption.web.responseHandlers.GetAllSheetNames;
import com.jack.algera.gahlificapption.web.responseHandlers.GetCategoryBudget;

public class BudgetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnAsyncTaskCompleted {

    private static final String SHEET_TO_IGNORE = "VueGlobal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Spinner selectSheetNames = (Spinner) findViewById(R.id.selectSheetName);
        Spinner selectCategories = (Spinner) findViewById(R.id.selectCategories);

        GetAllSheetNames getAllSheetNames = new GetAllSheetNames(this, selectSheetNames);
        getAllSheetNames.start();

        GetAllCategories getAllCategories = new GetAllCategories(this, selectCategories);
        getAllCategories.start();

        EditText entryDescription = (EditText) findViewById(R.id.entryDescription);
        EditText entryCost = (EditText) findViewById(R.id.entryCost);
        Button sendRequestButton = (Button) findViewById(R.id.send_request_button);

        selectSheetNames.setOnItemSelectedListener(this);
        selectCategories.setOnItemSelectedListener(this);

        sendRequestButton.setOnClickListener(view -> {
            String sheetName = selectSheetNames.getSelectedItem().toString();
            String category = selectCategories.getSelectedItem().toString();
            String cost = entryCost.getText().toString();
            String description = entryDescription.getText().toString();

            AddCategoryValue addCategoryValue = new AddCategoryValue(getApplicationContext(), sheetName, category, new AddCategoryEntryRequest(description, Float.parseFloat(cost)), this);
            addCategoryValue.start();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        setBudgetEntriesContainer();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setBudgetEntriesContainer() {
        Spinner selectSheetNames = findViewById(R.id.selectSheetName);
        Spinner selectCategories = findViewById(R.id.selectCategories);

        if (selectSheetNames != null && selectSheetNames.getSelectedItem() != null && !selectSheetNames.getSelectedItem().equals(SHEET_TO_IGNORE)) {
            LinearLayout linearLayout = findViewById(R.id.budgetEntriesContainer);
            TextView titleTotalCostTextView = findViewById(R.id.budgetEntryTitleCost);
            GetCategoryBudget getCategoryBudget = new GetCategoryBudget(selectSheetNames.getSelectedItem().toString(), selectCategories.getSelectedItem().toString(), linearLayout, titleTotalCostTextView, getFragmentManager(), this);
            getCategoryBudget.start();
        }
    }

    @Override
    public void onTaskCompleted() {
        setBudgetEntriesContainer();
    }
}