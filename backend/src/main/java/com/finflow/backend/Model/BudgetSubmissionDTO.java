package com.finflow.backend.Model;

import java.util.List;

public class BudgetSubmissionDTO {
    private Integer userId;
    private Double income;
    private String month;
    private List<CategoryBudgetDTO> categories;

    public BudgetSubmissionDTO(Integer userId, Double income, String month, List<CategoryBudgetDTO> categories) {
        this.userId = userId;
        this.income = income;
        this.month = month;
        this.categories = categories;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<CategoryBudgetDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryBudgetDTO> categories) {
        this.categories = categories;
    }


    public static class CategoryBudgetDTO {
        private String category;
        private Double budget;

        public CategoryBudgetDTO(String category, Double budget) {
            this.category = category;
            this.budget = budget;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getBudget() {
            return budget;
        }

        public void setBudget(Double budget) {
            this.budget = budget;
        }

        // Getters and setters
    }
    // Getters and setters
}
