package com.jack.algera.gahlificapption.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jack.algera.gahlificapption.MainActivity;
import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.budget.BudgetActivity;

public class MainFloatingActionFragment extends Fragment {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    private FloatingActionButton mainButton;
    private FloatingActionButton homeButton;
    private FloatingActionButton budgetButton;

    private boolean clicked = false;

    public MainFloatingActionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_floating_action, container, false);

        rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);
        mainButton = view.findViewById(R.id.main_fab_button);
        homeButton = view.findViewById(R.id.home_fab_button);
        budgetButton = view.findViewById(R.id.budget_fab_button);

        setupAnimations();

        return view;
    }

    private void setupAnimations() {
        mainButton.setOnClickListener(view -> {
            onMainButtonClicked();
        });

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        budgetButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), BudgetActivity.class);
            startActivity(intent);
        });
    }

    private void onMainButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            homeButton.setVisibility(View.VISIBLE);
            budgetButton.setVisibility(View.VISIBLE);
        } else {
            homeButton.setVisibility(View.INVISIBLE);
            budgetButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setClickable(boolean clicked) {
        homeButton.setClickable(!clicked);
        budgetButton.setClickable(!clicked);
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            mainButton.startAnimation(rotateOpen);
            homeButton.startAnimation(fromBottom);
            budgetButton.setAnimation(fromBottom);
        } else {
            mainButton.startAnimation(rotateClose);
            homeButton.startAnimation(toBottom);
            budgetButton.setAnimation(toBottom);
        }
    }
}