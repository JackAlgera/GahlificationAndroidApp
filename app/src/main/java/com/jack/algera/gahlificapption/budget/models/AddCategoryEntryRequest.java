package com.jack.algera.gahlificapption.budget.models;

import java.io.Serializable;

public class AddCategoryEntryRequest implements Serializable {
    private String description;
    private float cost;

    public AddCategoryEntryRequest() {
    }

    public AddCategoryEntryRequest(String description, float cost) {
        this.description = description;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
