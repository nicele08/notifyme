package com.nicele08.notifyme.service;

import com.nicele08.notifyme.entity.RequestLimit;
import com.nicele08.notifyme.repository.RequestLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestLimitService {

    private final RequestLimitRepository requestLimitRepository;

    @Autowired
    public RequestLimitService(RequestLimitRepository requestLimitRepository) {
        this.requestLimitRepository = requestLimitRepository;
    }

    public Optional<RequestLimit> getRequestLimitById(Long id) {
        return requestLimitRepository.findById(id);
    }

    public RequestLimit saveRequestLimit(RequestLimit requestLimit) {
        return requestLimitRepository.save(requestLimit);
    }

    public void deleteRequestLimit(RequestLimit requestLimit) {
        requestLimitRepository.delete(requestLimit);
    }
}
