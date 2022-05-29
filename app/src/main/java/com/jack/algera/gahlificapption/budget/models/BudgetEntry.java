package com.jack.algera.gahlificapption.budget.models;

import java.io.Serializable;

public class BudgetEntry implements Serializable {
    String description;
    Float cost;

    public BudgetEntry() {
    }

    public BudgetEntry(String description, Float cost) {
        this.description = description;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
