package com.finflow.backend.Service;


import com.finflow.backend.Model.BudgetCategory;
import com.finflow.backend.Model.Spending;
import com.finflow.backend.Model.SpendingUpdateDTO;
import com.finflow.backend.Model.User;
import com.finflow.backend.Repository.BudgetCategoryRepository;
import com.finflow.backend.Repository.SpendingRepository;
import com.finflow.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpendingService {

    @Autowired
    private SpendingRepository spendingRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    public Spending createSpending(Spending spending) {
        return spendingRepository.save(spending);
    }

    public List<Spending> getAllSpendingsByUser(Integer userId) {
        return spendingRepository.findByUserId(userId);
    }

    public Optional<Spending> getSpendingById(Integer id) {
        return spendingRepository.findById(id);
    }

    public Spending updateSpending(Integer id, Spending updatedSpending) {
        return spendingRepository.findById(id)
                .map(spending -> {
                    spending.setActualAmount(updatedSpending.getActualAmount());
                    spending.setMonth(updatedSpending.getMonth());
                    spending.setCategory(updatedSpending.getCategory());
                    spending.setUser(updatedSpending.getUser());
                    return spendingRepository.save(spending);
                })
                .orElseThrow(() -> new RuntimeException("Spending not found with id " + id));
    }

    public void deleteSpending(Integer id) {
        spendingRepository.deleteById(id);
    }





    public Spending updateActualAmount(SpendingUpdateDTO dto) {
        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        /*BudgetCategory category = budgetCategoryRepository.findByNameAndUserId(dto.categoryName, dto.userId)
                .orElseThrow(() -> new RuntimeException("Category not found"));*/

        Optional<Spending> existing = spendingRepository.findByUserIdAndCategoryNameAndMonth(dto.userId, dto.categoryName, dto.month);

        Spending spending = existing.orElse(new Spending());
        /*spending.setUser(user);*/
        /*spending.setCategory(category);*/
        /*spending.setMonth(dto.month);*/
        spending.setActualAmount(dto.actualAmount);

        return spendingRepository.save(spending);
    }

}
