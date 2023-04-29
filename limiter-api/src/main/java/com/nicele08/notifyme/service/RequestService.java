package com.nicele08.notifyme.service;

import com.nicele08.notifyme.entity.Request;
import com.nicele08.notifyme.entity.RequestLimit;
import com.nicele08.notifyme.repository.RequestLimitRepository;
import com.nicele08.notifyme.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestLimitRepository requestLimitRepository;

    public Request createRequest(Request request) {
        Long clientId = request.getClient().getId();
        List<RequestLimit> limits = requestLimitRepository.findByClientId(clientId);
        RequestLimit requestLimit = limits.stream().findFirst().orElseThrow(() -> new RuntimeException("No request limits found for client with ID " + clientId));

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minus(requestLimit.getTimeWindow());
        List<Request> requests = requestRepository.findByClientAndCreatedAtBetween(request.getClient(), startTime, currentTime);

        if (requests.size() >= requestLimit.getMaxRequests()) {
            throw new RuntimeException("Too many requests within time window for client " + clientId);
        }

        request.setCreatedAt(currentTime);
        return requestRepository.save(request);
    }
}
