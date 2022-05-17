package com.jack.algera.gahlificapption.budget.models;

public class AddCategoryEntryResponse {
    private String cellPosition;

    public AddCategoryEntryResponse() {
    }

    public AddCategoryEntryResponse(String cellPosition) {
        this.cellPosition = cellPosition;
    }

    public String getCellPosition() {
        return cellPosition;
    }

    public void setCellPosition(String cellPosition) {
        this.cellPosition = cellPosition;
    }
}
