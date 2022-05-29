package com.jack.algera.gahlificapption.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.budget.models.BudgetEntry;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetEntryFragment extends Fragment {
    private static final String BUDGET_DESCRIPTION = "BUDGET_DESCRIPTION";
    private static final String BUDGET_COST = "BUDGET_COST";

    private BudgetEntry budgetEntry;

    public BudgetEntryFragment() {
    }

    public static BudgetEntryFragment newInstance(BudgetEntry budgetEntry) {
        BudgetEntryFragment fragment = new BudgetEntryFragment();
        Bundle b = new Bundle();
        b.putString(BUDGET_DESCRIPTION, budgetEntry.getDescription());
        b.putFloat(BUDGET_COST, budgetEntry.getCost());
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            budgetEntry = new BudgetEntry(getArguments().getString(BUDGET_DESCRIPTION), getArguments().getFloat(BUDGET_COST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_budget_entry, container, false);
        ((TextView) v.findViewById(R.id.budgetEntryCost)).setText(budgetEntry.getCost().toString());
        ((TextView) v.findViewById(R.id.budgetEntryDescription)).setText(budgetEntry.getDescription());
        return v;
    }
}