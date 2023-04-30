package com.nicele08.notifyme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicele08.notifyme.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByApiKey(String apiKey);
}