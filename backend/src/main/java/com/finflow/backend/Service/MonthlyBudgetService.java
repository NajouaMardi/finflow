package com.finflow.backend.Service;

import com.finflow.backend.Model.MonthlyBudget;
import com.finflow.backend.Repository.MonthlyBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.YearMonth;

@Service
public class MonthlyBudgetService {

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;


    public MonthlyBudget createBudget(MonthlyBudget budget) {
        return monthlyBudgetRepository.save(budget);
    }

    public List<MonthlyBudget> getBudgetsByUser(Integer userId) {
        return monthlyBudgetRepository.findByUserId(userId);
    }

    public List<MonthlyBudget> getBudgetsByUserAndMonth(Integer userId, String month) {
        return monthlyBudgetRepository.findByUserIdAndMonth(userId, month);
    }

    public Optional<MonthlyBudget> getBudgetById(Integer id) {
        return monthlyBudgetRepository.findById(id);
    }

    public MonthlyBudget updateBudget(Integer id, MonthlyBudget updatedBudget) {
        return monthlyBudgetRepository.findById(id)
                .map(budget -> {
                    budget.setMonth(updatedBudget.getMonth());
                    budget.setPlannedAmount(updatedBudget.getPlannedAmount());
                    budget.setUser(updatedBudget.getUser());
                    budget.setCategory(updatedBudget.getCategory());
                    return monthlyBudgetRepository.save(budget);
                })
                .orElseThrow(() -> new RuntimeException("MonthlyBudget not found with id " + id));
    }

    public void deleteBudget(Integer id) {
        monthlyBudgetRepository.deleteById(id);
    }
}
