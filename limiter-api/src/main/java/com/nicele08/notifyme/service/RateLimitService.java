package com.nicele08.notifyme.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.entity.RequestLimit;

@Service
public class RateLimitService {

    private final RequestService requestService;
    private final MonthlyRequestLimitService monthlyRequestLimitService;
    private final RequestLimitService requestLimitService;

    public RateLimitService(RequestService requestService, MonthlyRequestLimitService monthlyRequestLimitService,
            RequestLimitService requestLimitService) {
        this.requestService = requestService;
        this.monthlyRequestLimitService = monthlyRequestLimitService;
        this.requestLimitService = requestLimitService;
    }

    public int totalMonthlyRequests(RequestLimit requestLimit) {
        LocalDate now = LocalDate.now();

        // Check if the total number of requests in the current month exceeds the
        // monthly request limit
        int totalRequests = requestService.getRequestCountForMonthAndClient(now.getMonthValue(),
                now.getYear(), requestLimit.getClient().getId());

        return totalRequests;
    }

    public int countRequestsWithinTimeWindow(RequestLimit requestLimit) {
        LocalDateTime now = LocalDateTime.now();

        // Check if the total number of requests within the time window exceeds the
        // request limit
        int requestsWithinTimeWindow = requestService.getRequestCountWithinTimeWindowAndClient(
                now.minus(requestLimit.getTimeWindow()), now,
                requestLimit.getClient().getId());

        return requestsWithinTimeWindow;
    }

    public RequestLimit findOrCreateRequestLimit(Client client) {
        LocalDate now = LocalDate.now();
        Optional<RequestLimit> optionalRequestLimit = requestLimitService
                .findByClientIdAndMonthlyRequestLimitMonth(client.getId(), now.getMonthValue());

        if (optionalRequestLimit.isPresent()) {
            return optionalRequestLimit.get();
        }

        MonthlyRequestLimit newMonthlyRequestLimit = new MonthlyRequestLimit();
        newMonthlyRequestLimit.setClient(client);
        newMonthlyRequestLimit.setMaxRequests(1000);
        newMonthlyRequestLimit.setMonth(LocalDate.now().getMonthValue());

        RequestLimit requestLimit = new RequestLimit();
        requestLimit.setClient(client);
        requestLimit.setMaxRequests(100);
        requestLimit.setTimeWindow(Duration.ofHours(1));
        requestLimit.setCurrentDateTime();
        requestLimit.setMonthlyRequestLimit(monthlyRequestLimitService.save(newMonthlyRequestLimit));

        return requestLimitService.save(requestLimit);

    }

}
