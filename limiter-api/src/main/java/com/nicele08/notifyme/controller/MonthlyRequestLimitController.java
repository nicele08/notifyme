package com.nicele08.notifyme.controller;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.model.MonthlyRequestLimitRequestBody;
import com.nicele08.notifyme.service.ClientService;
import com.nicele08.notifyme.service.MonthlyRequestLimitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthly-limits")
@Tag(name = "Monthly Limits", description = "The Monthly Limits API")
public class MonthlyRequestLimitController {

    private final MonthlyRequestLimitService monthlyRequestLimitService;
    private final ClientService clientService;

    public MonthlyRequestLimitController(MonthlyRequestLimitService monthlyRequestLimitService,
            ClientService clientService) {
        this.monthlyRequestLimitService = monthlyRequestLimitService;
        this.clientService = clientService;
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Get monthly limits by client id")
    public ResponseEntity<List<MonthlyRequestLimit>> getMonthlyLimitsByClientId(@PathVariable Long clientId) {
        List<MonthlyRequestLimit> monthlyLimits = monthlyRequestLimitService.findByClientId(clientId);
        return new ResponseEntity<>(monthlyLimits, HttpStatus.OK);
    }

    @GetMapping("/{clientId}/{month}")
    @Operation(summary = "Get monthly limit by client id and month")
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
    @Operation(summary = "Create monthly limit")
    public ResponseEntity<MonthlyRequestLimit> createMonthlyLimit(
            @RequestBody @Valid MonthlyRequestLimitRequestBody monthlyRequestLimitRequestBody) {
        Long clientId = monthlyRequestLimitRequestBody.getClientId();
        LocalDate month = LocalDate.now();
        Optional<MonthlyRequestLimit> presentMonthlyLimit = monthlyRequestLimitService.findByClientIdAndMonth(clientId,
                month);
        if (presentMonthlyLimit.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Optional<Client> optionalClient = clientService.getClientById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            MonthlyRequestLimit monthlyLimit = new MonthlyRequestLimit();
            monthlyLimit.setMaxRequests(monthlyRequestLimitRequestBody.getMaxRequests());
            monthlyLimit.setMonth(month.getMonthValue());
            monthlyLimit.setClient(client);
            MonthlyRequestLimit createdMonthlyLimit = monthlyRequestLimitService.save(monthlyLimit);
            return new ResponseEntity<>(createdMonthlyLimit, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
