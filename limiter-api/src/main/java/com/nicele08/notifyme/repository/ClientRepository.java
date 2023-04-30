package com.nicele08.notifyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicele08.notifyme.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}