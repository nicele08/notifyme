package com.nicele08.notifyme.controller;

import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.service.MonthlyRequestLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthly-limits")
public class MonthlyRequestLimitController {

    private final MonthlyRequestLimitService monthlyRequestLimitService;

    @Autowired
    public MonthlyRequestLimitController(MonthlyRequestLimitService monthlyRequestLimitService) {
        this.monthlyRequestLimitService = monthlyRequestLimitService;
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<MonthlyRequestLimit>> getMonthlyLimitsByClientId(@PathVariable Long clientId) {
        List<MonthlyRequestLimit> monthlyLimits = monthlyRequestLimitService.findByClientId(clientId);
        return new ResponseEntity<>(monthlyLimits, HttpStatus.OK);
    }

    @GetMapping("/{clientId}/{month}")
    public ResponseEntity<MonthlyRequestLimit> getMonthlyLimitByClientIdAndMonth(
            @PathVariable Long clientId,
            @PathVariable String month) {

        LocalDate date = LocalDate.parse(month);
        Optional<MonthlyRequestLimit> monthlyLimit = monthlyRequestLimitService.findByClientIdAndMonth(clientId, date);

        return monthlyLimit
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<MonthlyRequestLimit> createMonthlyLimit(@RequestBody MonthlyRequestLimit monthlyLimit) {
        MonthlyRequestLimit createdMonthlyLimit = monthlyRequestLimitService.save(monthlyLimit);
        return new ResponseEntity<>(createdMonthlyLimit, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMonthlyLimit(@RequestBody MonthlyRequestLimit monthlyLimit) {
        monthlyRequestLimitService.delete(monthlyLimit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
