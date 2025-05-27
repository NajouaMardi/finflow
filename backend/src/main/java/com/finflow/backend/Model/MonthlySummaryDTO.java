package com.finflow.backend.Model;

public class MonthlySummaryDTO {
    private double totalIncome;
    private double totalBudget;
    private double totalSpending;

    public MonthlySummaryDTO(double totalIncome, double totalBudget, double totalSpending) {
        this.totalIncome = totalIncome != 0.0 ? totalIncome : 0;
        this.totalBudget = totalBudget != 0.0 ? totalBudget : 0;
        this.totalSpending = totalSpending != 0.0 ? totalSpending : 0;
    }

    // Getters and setters

    public double getTotalIncome() { return totalIncome; }
    public void setTotalIncome(double totalIncome) { this.totalIncome = totalIncome; }
    public double getTotalBudget() { return totalBudget; }
    public void setTotalBudget(double totalBudget) { this.totalBudget = totalBudget; }
    public double getTotalSpending() { return totalSpending; }
    public void setTotalSpending(double totalSpending) { this.totalSpending = totalSpending; }

    public Double getSavedAmount() {
        return totalIncome - totalSpending;
    }
}
