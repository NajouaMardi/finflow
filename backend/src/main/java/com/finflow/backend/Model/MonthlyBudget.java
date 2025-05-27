package com.finflow.backend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.YearMonth;

@Entity
@Table(name="monthlyBudget")
@EntityListeners(AuditingEntityListener.class)
public class MonthlyBudget {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String month; //form is "YYYY-MM"
    private Double plannedAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference // Avoid loop from BudgetCategory
    private BudgetCategory category;

    public MonthlyBudget(Integer id, String month, Double plannedAmount, User user, BudgetCategory category) {
        this.id = id;
        this.month = month;
        this.plannedAmount = plannedAmount;
        this.user = user;
        this.category = category;
    }

    public MonthlyBudget() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BudgetCategory getCategory() {
        return category;
    }

    public void setCategory(BudgetCategory category) {
        this.category = category;
    }
}

