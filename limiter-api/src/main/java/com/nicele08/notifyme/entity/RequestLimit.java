package com.nicele08.notifyme.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "request_limit")
public class RequestLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonSerialize(using = DurationSerializer.class)
    private Duration timeWindow;

    @Column(nullable = false)
    private Integer maxRequests;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_request_limit_id")
    @JsonBackReference
    private MonthlyRequestLimit monthlyRequestLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(Duration timeWindow) {
        this.timeWindow = timeWindow;
    }

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MonthlyRequestLimit getMonthlyRequestLimit() {
        return monthlyRequestLimit;
    }

    public void setMonthlyRequestLimit(MonthlyRequestLimit monthlyRequestLimit) {
        this.monthlyRequestLimit = monthlyRequestLimit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCurrentDateTime() {
        this.createdAt = LocalDateTime.now();
    }
    
}
