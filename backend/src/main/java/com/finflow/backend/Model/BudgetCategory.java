package com.finflow.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name="budgetCategory")
@EntityListeners(AuditingEntityListener.class)
public class BudgetCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference // This owns the MonthlyBudget relationship
    private MonthlyBudget monthlyBudget;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference // This owns the Spending list
    private List<Spending> spendings;

    @ManyToOne
    @JoinColumn(name = "monthlyIncome_id")
    private MonthlyIncome income;

    public BudgetCategory(Integer id, String name, User user, MonthlyBudget monthlyBudget, List<Spending> spendings, MonthlyIncome income) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.monthlyBudget = monthlyBudget;
        this.spendings = spendings;
        this.income = income;
    }

    public BudgetCategory() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MonthlyBudget getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(MonthlyBudget monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public List<Spending> getSpendings() {
        return spendings;
    }

    public void setSpendings(List<Spending> spendings) {
        this.spendings = spendings;
    }

    public MonthlyIncome getIncome() {
        return income;
    }

    public void setIncome(MonthlyIncome income) {
        this.income = income;
    }
}
