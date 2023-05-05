package com.nicele08.notifyme.repository;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import com.nicele08.notifyme.entity.RequestLimit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLimitRepository extends JpaRepository<RequestLimit, Long> {
    List<RequestLimit> findByClientId(Long clientId);

    Optional<RequestLimit> findByClientAndMonthlyRequestLimit(Client client, MonthlyRequestLimit monthlyRequestLimit);
}
