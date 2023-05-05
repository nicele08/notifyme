package com.nicele08.notifyme.service;

import com.nicele08.notifyme.entity.Request;
import com.nicele08.notifyme.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public int getRequestCountForMonthAndClient(int month, int year, Long clientId) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        return requestRepository.countByCreatedAtBetweenAndClientId(clientId, startOfMonth, endOfMonth);
    }

    public int getRequestCountWithinTimeWindowAndClient(LocalDateTime fromDateTime, LocalDateTime toDateTime,
            Long clientId) {
        return requestRepository.countByCreatedAtBetweenAndClientId(clientId, fromDateTime, toDateTime);
    }
}
