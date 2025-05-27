package com.finflow.backend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name="spending")
@EntityListeners(AuditingEntityListener.class)
public class Spending {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String month; //form is "YYYY-MM"
    private Double actualAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private BudgetCategory category;



    public Spending(Integer id, String month, Double actualAmount, User user, BudgetCategory category) {
        this.id = id;
        this.month = month;
        this.actualAmount = actualAmount;
        this.user = user;
        this.category = category;
    }

    public Spending() {

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

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
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
