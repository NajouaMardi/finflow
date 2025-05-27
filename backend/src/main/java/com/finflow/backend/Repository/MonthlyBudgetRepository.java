package com.finflow.backend.Repository;


import com.finflow.backend.Model.BudgetCategory;
import com.finflow.backend.Model.MonthlyBudget;
import com.finflow.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Integer> {
    List<MonthlyBudget> findByUserId(Integer userId);
    List<MonthlyBudget> findByUserIdAndMonth(Integer userId, String month);


    @Modifying
    @Transactional
    @Query("DELETE FROM MonthlyBudget m WHERE m.user = :user AND m.category.name = :categoryName AND m.month = :month")
    void deleteByUserAndCategoryNameAndMonth(@Param("user") User user,
                                             @Param("categoryName") String categoryName,
                                             @Param("month") String month);




    Optional<MonthlyBudget> findByCategoryAndMonth(BudgetCategory category, String month);
}
