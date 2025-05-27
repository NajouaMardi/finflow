package com.finflow.backend.Controller;

import com.finflow.backend.Model.MonthlyBudget;
import com.finflow.backend.Service.MonthlyBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.time.YearMonth;

@RestController
@RequestMapping("/monthlybudgets")
public class MonthlyBudgetController {

    @Autowired
    private MonthlyBudgetService monthlyBudgetService;

    public MonthlyBudgetController(MonthlyBudgetService monthlyBudgetService) {
        this.monthlyBudgetService = monthlyBudgetService;
    }

    @PostMapping
    public ResponseEntity<MonthlyBudget> createBudget(@RequestBody MonthlyBudget budget) {
        MonthlyBudget created = monthlyBudgetService.createBudget(budget);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MonthlyBudget>> getBudgetsByUser(@PathVariable Integer userId) {
        List<MonthlyBudget> budgets = monthlyBudgetService.getBudgetsByUser(userId);
        return ResponseEntity.ok(budgets);
    }

    // Optional: filter budgets by user and month, passing month as a string like "2025-05"
    @GetMapping("/user/{userId}/month/{month}")
    public ResponseEntity<List<MonthlyBudget>> getBudgetsByUserAndMonth(
            @PathVariable Integer userId,
            @PathVariable String month) {
        try {

            List<MonthlyBudget> budgets = monthlyBudgetService.getBudgetsByUserAndMonth(userId, month);
            return ResponseEntity.ok(budgets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyBudget> getBudgetById(@PathVariable Integer id) {
        Optional<MonthlyBudget> budget = monthlyBudgetService.getBudgetById(id);
        return budget.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyBudget> updateBudget(@PathVariable Integer id, @RequestBody MonthlyBudget budget) {
        try {
            MonthlyBudget updated = monthlyBudgetService.updateBudget(id, budget);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Integer id) {
        monthlyBudgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
