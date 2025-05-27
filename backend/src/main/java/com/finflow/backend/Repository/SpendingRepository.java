package com.finflow.backend.Repository;

import com.finflow.backend.Model.Spending;
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
public interface SpendingRepository extends JpaRepository<Spending, Integer> {
    List<Spending> findByUserId(Integer userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Spending s WHERE s.user = :user AND s.category.name = :categoryName AND s.month = :month")
    void deleteByUserAndCategoryNameAndMonth(@Param("user") User user,
                                             @Param("categoryName") String categoryName,
                                             @Param("month") String month);


    Optional<Spending> findByUserIdAndCategoryIdAndMonth(Integer userId, Integer categoryId, String month);

    @Query("SELECT s FROM Spending s JOIN s.category c WHERE s.user.id = :userId AND c.name = :categoryName AND s.month = :month")
    Optional<Spending> findByUserIdAndCategoryNameAndMonth(@Param("userId") Integer userId,
                                                           @Param("categoryName") String categoryName,
                                                           @Param("month") String month);


}