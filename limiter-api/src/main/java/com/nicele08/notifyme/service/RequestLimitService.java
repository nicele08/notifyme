package com.nicele08.notifyme.service;

import com.nicele08.notifyme.entity.RequestLimit;
import com.nicele08.notifyme.repository.RequestLimitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestLimitService {

    private final RequestLimitRepository requestLimitRepository;

    public RequestLimitService(RequestLimitRepository requestLimitRepository) {
        this.requestLimitRepository = requestLimitRepository;
    }

    public Optional<RequestLimit> getRequestLimitById(Long id) {
        return requestLimitRepository.findById(id);
    }

    public Optional<RequestLimit> findByClientIdAndMonthlyRequestLimitMonth(Long clientId,
            int month) {
        return requestLimitRepository.findByClient_IdAndMonthlyRequestLimit_Month(clientId, month);
    }

    public RequestLimit saveRequestLimit(RequestLimit requestLimit) {
        return requestLimitRepository.save(requestLimit);
    }

    public void deleteRequestLimit(RequestLimit requestLimit) {
        requestLimitRepository.delete(requestLimit);
    }

    public RequestLimit save(RequestLimit requestLimit) {
        return requestLimitRepository.save(requestLimit);
    }
}
