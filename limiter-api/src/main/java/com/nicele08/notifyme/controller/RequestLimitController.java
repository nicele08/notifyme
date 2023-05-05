package com.nicele08.notifyme.controller;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.entity.RequestLimit;
import com.nicele08.notifyme.exception.NotFoundException;
import com.nicele08.notifyme.model.RequestLimitBody;
import com.nicele08.notifyme.service.ClientService;
import com.nicele08.notifyme.service.MonthlyRequestLimitService;
import com.nicele08.notifyme.service.RequestLimitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/request-limits")
@Tag(name = "Request Limits", description = "The Request Limits API")
public class RequestLimitController {

    private final RequestLimitService requestLimitService;
    private final ClientService clientService;
    private final MonthlyRequestLimitService monthlyRequestLimitService;

    public RequestLimitController(RequestLimitService requestLimitService, ClientService clientService,
            MonthlyRequestLimitService monthlyRequestLimitService) {
        this.requestLimitService = requestLimitService;
        this.clientService = clientService;
        this.monthlyRequestLimitService = monthlyRequestLimitService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a request limit by id")
    public ResponseEntity<RequestLimit> getRequestLimitById(@PathVariable Long id) {
        Optional<RequestLimit> requestLimit = requestLimitService.getRequestLimitById(id);

        return requestLimit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a request limit")
    public ResponseEntity<RequestLimit> createRequestLimit(@RequestBody @Valid RequestLimitBody requestLimitBody) {
        Long clientId = requestLimitBody.getClientId();
        Optional<Client> optionalClient = clientService.getClientById(clientId);

        Long monthlyRequestLimitId = requestLimitBody.getMonthlyRequestLimitId();
        Optional<MonthlyRequestLimit> optionalMonthlyRequestLimit = monthlyRequestLimitService
                .findByIdAndClientId(monthlyRequestLimitId, clientId);
        if (optionalClient.isPresent() && optionalMonthlyRequestLimit.isPresent()) {
            Client client = optionalClient.get();
            MonthlyRequestLimit monthlyRequestLimit = optionalMonthlyRequestLimit.get();
            RequestLimit requestLimit = new RequestLimit();
            requestLimit.setClient(client);
            requestLimit.setMaxRequests(requestLimitBody.getMaxRequests());
            requestLimit.setTimeWindow(requestLimitBody.getTimeWindow());
            requestLimit.setMonthlyRequestLimit(monthlyRequestLimit);
            requestLimit.setCurrentDateTime();
            RequestLimit createdRequestLimit = requestLimitService.saveRequestLimit(requestLimit);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestLimit);
        } else {
            throw new NotFoundException("Client or monthly request limit not found with id " + clientId);
        }

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a request limit")
    public ResponseEntity<RequestLimit> updateRequestLimit(@PathVariable Long id,
            @RequestBody @Valid RequestLimitBody requestLimitBody) {
        Optional<RequestLimit> existingRequestLimit = requestLimitService.getRequestLimitById(id);

        if (existingRequestLimit.isPresent()) {
            Long clientId = requestLimitBody.getClientId();
            Optional<Client> optionalClient = clientService.getClientById(clientId);

            Long monthlyRequestLimitId = requestLimitBody.getMonthlyRequestLimitId();
            Optional<MonthlyRequestLimit> optionalMonthlyRequestLimit = monthlyRequestLimitService
                    .findByIdAndClientId(monthlyRequestLimitId, clientId);
            if (optionalClient.isPresent() && optionalMonthlyRequestLimit.isPresent()) {
                Client client = optionalClient.get();
                MonthlyRequestLimit monthlyRequestLimit = optionalMonthlyRequestLimit.get();
                RequestLimit requestLimit = new RequestLimit();
                requestLimit.setId(id);
                requestLimit.setClient(client);
                requestLimit.setMaxRequests(requestLimitBody.getMaxRequests());
                requestLimit.setTimeWindow(requestLimitBody.getTimeWindow());
                requestLimit.setMonthlyRequestLimit(monthlyRequestLimit);
                RequestLimit updatedRequestLimit = requestLimitService.saveRequestLimit(requestLimit);
                return ResponseEntity.status(HttpStatus.OK).body(updatedRequestLimit);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            throw new NotFoundException("Request limit not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a request limit")
    public ResponseEntity<HttpStatus> deleteRequestLimit(@PathVariable Long id) {
        Optional<RequestLimit> requestLimit = requestLimitService.getRequestLimitById(id);

        if (requestLimit.isPresent()) {
            requestLimitService.deleteRequestLimit(requestLimit.get());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new NotFoundException("Request limit not found with id " + id);
        }
    }

}
