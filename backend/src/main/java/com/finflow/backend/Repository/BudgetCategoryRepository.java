package com.finflow.backend.Repository;


import com.finflow.backend.Model.BudgetCategory;
import com.finflow.backend.Model.CategorySummary;
import com.finflow.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Integer> {
    List<BudgetCategory> findByUserId(Integer userId);


    @Query("SELECT new com.finflow.backend.Model.CategorySummary(" +
            "c.name, mb.month, mb.plannedAmount, SUM(s.actualAmount)) " +
            "FROM BudgetCategory c " +
            "JOIN c.monthlyBudget mb " +
            "LEFT JOIN c.spendings s ON s.month = mb.month " +
            "WHERE c.user.id = :userId " +
            "GROUP BY c.name, mb.month, mb.plannedAmount")
    List<CategorySummary> getCategorySummaries(@Param("userId") Integer userId);





    @Modifying
    @Transactional
    @Query("DELETE FROM BudgetCategory c WHERE c.user = :user AND c.name = :categoryName")
    void deleteCategory(@Param("user") User user,
                        @Param("categoryName") String categoryName);







    Optional<BudgetCategory> findByNameAndUserId(String name, Integer userId);



}
