package com.finflow.backend.Controller;

import com.finflow.backend.Model.*;
import com.finflow.backend.Repository.*;
import com.finflow.backend.Service.BudgetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class BudgetCategoryController {

    @Autowired
    private BudgetCategoryService categoryService;

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private MonthlyIncomeRepository incomeRepository;

    @Autowired
    private MonthlyBudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpendingRepository spendingRepository;

    @PostMapping
    public ResponseEntity<BudgetCategory> createCategory(@RequestBody BudgetCategory category) {
        BudgetCategory created = categoryService.createCategory(category);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BudgetCategory>> getCategoriesByUser(@PathVariable Integer userId) {
        List<BudgetCategory> categories = categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetCategory> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetCategory> updateCategory(@PathVariable Integer id, @RequestBody BudgetCategory category) {
        try {
            BudgetCategory updated = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/summaries/{userId}")
    public List<CategorySummary> getSummaries(@PathVariable Integer userId) {
        List<CategorySummary> summaries = budgetCategoryRepository.getCategorySummaries(userId);
        for (CategorySummary cs : summaries) {
            if (cs.getActualAmount() == null) {
                cs.setActualAmount(0.0);
            }
        }
        return summaries;
    }







    @DeleteMapping("/deleteSummary/categorysummary")
    public ResponseEntity<?> deleteCategorySummary(
            @RequestParam Integer userId,
            @RequestParam String category,
            @RequestParam String month) {

        try {
            categoryService.deleteCategorySummary(userId, category, month);
            return ResponseEntity.ok("Category summary deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting category summary.");
        }
    }













    @PostMapping("/save")
    public ResponseEntity<?> saveBudget(@RequestBody BudgetSubmissionDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();

        // Save income
        MonthlyIncome income = new MonthlyIncome();
        income.setUser(user);
        income.setMonth(dto.getMonth());
        income.setAmount(dto.getIncome());
        income = incomeRepository.save(income);

        for (BudgetSubmissionDTO.CategoryBudgetDTO cb : dto.getCategories()) {
            // Save category
            BudgetCategory category = new BudgetCategory();
            category.setName(cb.getCategory());
            category.setUser(user);
            category.setIncome(income);
            category = budgetCategoryRepository.save(category);

            // Save budget
            MonthlyBudget budget = new MonthlyBudget();
            budget.setUser(user);
            budget.setCategory(category);
            budget.setMonth(dto.getMonth());
            budget.setPlannedAmount(cb.getBudget());

            budgetRepository.save(budget);
        }

        return ResponseEntity.ok("Budget saved");
    }













    @PostMapping("/addCategorySummary")
    public ResponseEntity<?> addCategorySummary(@RequestBody AddCategorySummaryRequest dto) {
        try {


            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));


                        BudgetCategory newCategory = new BudgetCategory();
                        newCategory.setName(dto.getCategory());
                        newCategory.setUser(user);
                        newCategory.setSpendings(new ArrayList<>());
                        BudgetCategory finalNewCategory = budgetCategoryRepository.save(newCategory);



            MonthlyBudget monthlyBudget = budgetRepository.findByCategoryAndMonth(finalNewCategory, dto.getMonth())
                    .orElseGet(() -> {
                        MonthlyBudget mb = new MonthlyBudget();
                        mb.setCategory(finalNewCategory);
                        mb.setMonth(dto.getMonth());
                        mb.setUser(user);
                        return mb;
                    });
            monthlyBudget.setPlannedAmount(dto.getPlannedAmount());
            budgetRepository.save(monthlyBudget);

            Spending spending = new Spending();
            spending.setCategory(finalNewCategory);
            spending.setUser(user);
            spending.setMonth(dto.getMonth());
            spending.setActualAmount(dto.getActualAmount());
            spendingRepository.save(spending);

            return ResponseEntity.ok("Category summary added successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
