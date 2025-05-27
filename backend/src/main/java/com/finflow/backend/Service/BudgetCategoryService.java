package com.finflow.backend.Service;

import com.finflow.backend.Model.BudgetCategory;
import com.finflow.backend.Model.User;
import com.finflow.backend.Repository.BudgetCategoryRepository;
import com.finflow.backend.Repository.MonthlyBudgetRepository;
import com.finflow.backend.Repository.SpendingRepository;
import com.finflow.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetCategoryService {

    @Autowired
    private BudgetCategoryRepository categoryRepository;

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;


    public BudgetCategory createCategory(BudgetCategory category) {
        return categoryRepository.save(category);
    }

    public List<BudgetCategory> getCategoriesByUser(Integer userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Optional<BudgetCategory> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public BudgetCategory updateCategory(Integer id, BudgetCategory updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    category.setUser(updatedCategory.getUser());
                    //Do NOT update monthlyBudget/spendings here to avoid unintended cascades
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }






    @Transactional
    public void deleteCategorySummary(Integer userId, String categoryName, String month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        spendingRepository.deleteByUserAndCategoryNameAndMonth(user, categoryName, month);
        monthlyBudgetRepository.deleteByUserAndCategoryNameAndMonth(user, categoryName, month);
        //categoryRepository.deleteCategory(user, categoryName);
    }


}
