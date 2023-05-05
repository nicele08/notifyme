package com.nicele08.notifyme.service;

import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.repository.MonthlyRequestLimitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyRequestLimitService {

    private final MonthlyRequestLimitRepository monthlyRequestLimitRepository;

    public MonthlyRequestLimitService(MonthlyRequestLimitRepository monthlyRequestLimitRepository) {
        this.monthlyRequestLimitRepository = monthlyRequestLimitRepository;
    }

    public List<MonthlyRequestLimit> findByClientId(Long clientId) {
        return monthlyRequestLimitRepository.findByClientId(clientId);
    }

    public Optional<MonthlyRequestLimit> findByClientIdAndMonth(Long clientId, LocalDate month) {
        return monthlyRequestLimitRepository.findByClientIdAndMonth(clientId, month.getMonthValue());
    }

    public Optional<MonthlyRequestLimit> findByIdAndClientId(Long id, Long clientId) {
        return monthlyRequestLimitRepository.findByIdAndClientId(id, clientId);
    }

    public MonthlyRequestLimit save(MonthlyRequestLimit monthlyRequestLimit) {
        return monthlyRequestLimitRepository.save(monthlyRequestLimit);
    }

    public void delete(MonthlyRequestLimit monthlyRequestLimit) {
        monthlyRequestLimitRepository.delete(monthlyRequestLimit);
    }
}
