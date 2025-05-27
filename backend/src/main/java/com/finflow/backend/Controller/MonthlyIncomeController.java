package com.finflow.backend.Controller;

import com.finflow.backend.Model.MonthlyIncome;
import com.finflow.backend.Model.MonthlySummaryDTO;
import com.finflow.backend.Repository.MonthlyIncomeRepository;
import com.finflow.backend.Service.MonthlyIncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")  //always preceed with api/v1 in all controller
@RequiredArgsConstructor
public class MonthlyIncomeController {


    @Autowired
    private MonthlyIncomeService incomeService;

    @Autowired
    private MonthlyIncomeRepository incomeRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MonthlyIncome>> getIncomesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(incomeService.getIncomesByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyIncome> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(incomeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MonthlyIncome> create(@RequestBody MonthlyIncome income) {
        return ResponseEntity.ok(incomeService.create(income));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyIncome> update(@PathVariable Integer id, @RequestBody MonthlyIncome income) {
        return ResponseEntity.ok(incomeService.update(id, income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        incomeService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{userId}/{month}")
    public MonthlySummaryDTO getMonthlySummary(@PathVariable Integer userId, @PathVariable String month) {
        return incomeRepository.getMonthlySummary(userId, month);
    }
}