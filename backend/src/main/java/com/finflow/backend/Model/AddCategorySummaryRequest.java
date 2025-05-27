package com.finflow.backend.Model;

public class AddCategorySummaryRequest {
    private Integer userId;
    private String category;
    private String month;
    private Double plannedAmount;
    private Double actualAmount;

    public AddCategorySummaryRequest(Integer userId, String category, String month, Double plannedAmount, Double actualAmount) {
        this.userId = userId;
        this.category = category;
        this.month = month;
        this.plannedAmount = plannedAmount;
        this.actualAmount = actualAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getPlannedAmount() {
        return plannedAmount;
    }

    public void setPlannedAmount(Double plannedAmount) {
        this.plannedAmount = plannedAmount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }
}
