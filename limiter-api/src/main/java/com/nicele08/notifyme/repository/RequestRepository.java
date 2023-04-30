package com.nicele08.notifyme.repository;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.Request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.client = :client AND r.createdAt BETWEEN :start AND :end")
    List<Request> findByClientAndCreatedAtBetween(Client client, LocalDateTime start, LocalDateTime end);
}
