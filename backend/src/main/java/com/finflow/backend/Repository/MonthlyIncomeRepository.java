package com.finflow.backend.Repository;

import com.finflow.backend.Model.MonthlyIncome;
import com.finflow.backend.Model.MonthlySummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, Integer> {
    List<MonthlyIncome> findByUserId(Integer userId);






    @Query("""
    select new com.finflow.backend.Model.MonthlySummaryDTO(
        (select coalesce(sum(m.amount), 0.0) from MonthlyIncome m where m.user.id = :userId and m.month = :month),
        (select coalesce(sum(b.plannedAmount), 0.0) from MonthlyBudget b where b.user.id = :userId and b.month = :month),
        (select coalesce(sum(s.actualAmount), 0.0) from Spending s where s.user.id = :userId and s.month = :month)
    )
""")
    MonthlySummaryDTO getMonthlySummary(@Param("userId") Integer userId, @Param("month") String month);

}