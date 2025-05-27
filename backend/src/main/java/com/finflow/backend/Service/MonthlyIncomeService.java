package com.finflow.backend.Service;

import com.finflow.backend.Model.MonthlyIncome;
import com.finflow.backend.Repository.MonthlyIncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyIncomeService {

    @Autowired
    private MonthlyIncomeRepository incomeRepository;

    public List<MonthlyIncome> getIncomesByUser(Integer userId) {
        return incomeRepository.findByUserId(userId);
    }

    public MonthlyIncome getById(Integer id) {
        return incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found"));
    }

    public MonthlyIncome create(MonthlyIncome income) {
        return incomeRepository.save(income);
    }

    public MonthlyIncome update(Integer id, MonthlyIncome updatedIncome) {
        MonthlyIncome income = getById(id);
        income.setAmount(updatedIncome.getAmount());
        income.setMonth(updatedIncome.getMonth());
        return incomeRepository.save(income);
    }

    public void delete(Integer id) {
        incomeRepository.deleteById(id);
    }
}