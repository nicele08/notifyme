package com.nicele08.notifyme.repository;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.Request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.client = :client AND r.createdAt BETWEEN :start AND :end")
    List<Request> findByClientAndCreatedAtBetween(Client client, LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.client.id = :clientId AND r.createdAt BETWEEN :startDate AND :endDate")
    int countByCreatedAtBetweenAndClientId(@Param("clientId") Long clientId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
