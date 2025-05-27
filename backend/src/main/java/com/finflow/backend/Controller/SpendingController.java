package com.finflow.backend.Controller;

import com.finflow.backend.Model.Spending;
import com.finflow.backend.Model.SpendingUpdateDTO;
import com.finflow.backend.Service.SpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spendings")
public class SpendingController {

    @Autowired
    private SpendingService spendingService;


    @PostMapping
    public ResponseEntity<Spending> createSpending(@RequestBody Spending spending) {
        Spending created = spendingService.createSpending(spending);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Spending>> getSpendingsByUser(@PathVariable Integer userId) {
        List<Spending> spendings = spendingService.getAllSpendingsByUser(userId);
        return ResponseEntity.ok(spendings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spending> getSpendingById(@PathVariable Integer id) {
        return spendingService.getSpendingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spending> updateSpending(@PathVariable Integer id, @RequestBody Spending spending) {
        try {
            Spending updated = spendingService.updateSpending(id, spending);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpending(@PathVariable Integer id) {
        spendingService.deleteSpending(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/update")
    public ResponseEntity<?> updateSpending(@RequestBody SpendingUpdateDTO dto) {
        Spending updated = spendingService.updateActualAmount(dto);
        return ResponseEntity.ok(updated);
    }
}
