package com.nicele08.notifyme.repository;

import com.nicele08.notifyme.entity.RequestLimit;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLimitRepository extends JpaRepository<RequestLimit, Long> {
    List<RequestLimit> findByClientId(Long clientId);

    Optional<RequestLimit> findByClient_IdAndMonthlyRequestLimit_Month(Long clientId, int month);
}
