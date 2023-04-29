package com.nicele08.notifyme.repository;

import com.nicele08.notifyme.entity.MonthlyRequestLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyRequestLimitRepository extends JpaRepository<MonthlyRequestLimit, Long> {

    List<MonthlyRequestLimit> findByClientId(Long clientId);

    Optional<MonthlyRequestLimit> findByClientIdAndMonth(Long clientId, LocalDate month);
}
