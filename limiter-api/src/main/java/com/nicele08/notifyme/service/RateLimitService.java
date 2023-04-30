package com.nicele08.notifyme.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.entity.RequestLimit;

@Service
public class RateLimitService {

    private final RequestService requestService;

    public RateLimitService(RequestService requestService) {
        this.requestService = requestService;
    }

    public boolean isRateLimited(Client client) {
        LocalDate now = LocalDate.now();

        // Get the current monthly request limit for the client
        Optional<MonthlyRequestLimit> optionalMonthlyRequestLimit = client.getMonthlyRequestLimits().stream()
                .filter(m -> m.getMonth().getMonth() == now.getMonth() && m.getMonth().getYear() == now.getYear())
                .findFirst();

        if (optionalMonthlyRequestLimit.isPresent()) {
            MonthlyRequestLimit monthlyRequestLimit = optionalMonthlyRequestLimit.get();

            // Check if the total number of requests in the current month exceeds the
            // monthly request limit
            int totalRequests = requestService.getRequestCountForMonthAndClient(now.getMonthValue(),
                    now.getYear(), client.getId());

            if (totalRequests >= monthlyRequestLimit.getMaxRequests()) {
                return true;
            }

            // Check if the client has any request limits that apply to the current time
            LocalDateTime fromDateTime = LocalDateTime.now().minus(monthlyRequestLimit.getMonth().lengthOfMonth(),
                    ChronoUnit.DAYS);

            Optional<RequestLimit> optionalRequestLimit = client.getRequestLimits().stream()
                    .filter(rl -> rl.getTimeWindow().compareTo(Duration.between(fromDateTime, LocalDateTime.now())) >= 0)
                    .findFirst();

            if (optionalRequestLimit.isPresent()) {
                RequestLimit requestLimit = optionalRequestLimit.get();

                // Check if the total number of requests within the time window exceeds the request limit
                int requestsWithinTimeWindow = requestService.getRequestCountWithinTimeWindowAndClient(
                        LocalDateTime.now().minus(requestLimit.getTimeWindow()), LocalDateTime.now(), client.getId());

                return requestsWithinTimeWindow >= requestLimit.getMaxRequests();
            }
        }

        return false;
    }
}
