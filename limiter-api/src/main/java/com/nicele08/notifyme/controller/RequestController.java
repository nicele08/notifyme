package com.nicele08.notifyme.controller;

import com.nicele08.notifyme.aspect.RateLimited;
import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.Request;
import com.nicele08.notifyme.exception.NotFoundException;
import com.nicele08.notifyme.model.RequestRequestBody;
import com.nicele08.notifyme.service.ClientService;
import com.nicele08.notifyme.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Requests", description = "The Requests API")
public class RequestController {

    private final RequestService requestService;
    private final ClientService clientService;

    public RequestController(RequestService requestService, ClientService clientService) {
        this.requestService = requestService;
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new request")
    @RateLimited(apiKeyArgName = "apiKey")
    public ResponseEntity<Request> createRequest(@RequestParam String apiKey,
            @RequestBody @Valid RequestRequestBody requestBody) {
        Optional<Client> optionalClient = clientService.findByApiKey(apiKey);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Request request = new Request();
            request.setType(requestBody.getType());
            request.setClient(client);
            request.setCurrentDateTime();
            Request createdRequest = requestService.createRequest(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
        } else {
            throw new NotFoundException("Client with api key " + apiKey + " does not exist");
        }
    }
}
