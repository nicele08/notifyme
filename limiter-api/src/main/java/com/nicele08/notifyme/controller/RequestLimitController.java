package com.nicele08.notifyme.controller;

import com.nicele08.notifyme.entity.RequestLimit;
import com.nicele08.notifyme.service.RequestLimitService;

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

    public RequestLimitController(RequestLimitService requestLimitService) {
        this.requestLimitService = requestLimitService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestLimit> getRequestLimitById(@PathVariable Long id) {
        Optional<RequestLimit> requestLimit = requestLimitService.getRequestLimitById(id);

        return requestLimit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RequestLimit> createRequestLimit(@RequestBody @Valid RequestLimit requestLimit) {
        RequestLimit createdRequestLimit = requestLimitService.saveRequestLimit(requestLimit);

        return new ResponseEntity<>(createdRequestLimit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestLimit> updateRequestLimit(@PathVariable Long id,
            @RequestBody @Valid RequestLimit requestLimit) {
        Optional<RequestLimit> existingRequestLimit = requestLimitService.getRequestLimitById(id);

        if (existingRequestLimit.isPresent()) {
            requestLimit.setId(id);
            RequestLimit updatedRequestLimit = requestLimitService.saveRequestLimit(requestLimit);

            return new ResponseEntity<>(updatedRequestLimit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRequestLimit(@PathVariable Long id) {
        Optional<RequestLimit> requestLimit = requestLimitService.getRequestLimitById(id);

        if (requestLimit.isPresent()) {
            requestLimitService.deleteRequestLimit(requestLimit.get());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
