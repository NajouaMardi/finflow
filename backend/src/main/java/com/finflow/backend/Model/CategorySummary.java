package com.finflow.backend.Model;

public class CategorySummary {
    private String category;
    private String month;
    private Double plannedAmount;
    private Double actualAmount;

    public CategorySummary(String category, String month, Double plannedAmount, Double actualAmount) {
        this.category = category;
        this.month = month;
        this.plannedAmount = plannedAmount;
        this.actualAmount = actualAmount;
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

    // Getters and setters
}
