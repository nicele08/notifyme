package com.nicele08.notifyme.controller;

import com.nicele08.notifyme.entity.Request;
import com.nicele08.notifyme.service.RequestService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Requests", description = "The Requests API")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody @Valid Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }
}
