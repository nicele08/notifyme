package com.nicele08.notifyme.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 64)
    private String apiKey;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MonthlyRequestLimit> monthlyRequestLimits = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequestLimit> requestLimits = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void addMonthlyRequestLimit(MonthlyRequestLimit monthlyRequestLimit) {
        monthlyRequestLimits.add(monthlyRequestLimit);
        monthlyRequestLimit.setClient(this);
    }

    public void removeMonthlyRequestLimit(MonthlyRequestLimit monthlyRequestLimit) {
        monthlyRequestLimits.remove(monthlyRequestLimit);
        monthlyRequestLimit.setClient(null);
    }

    public List<MonthlyRequestLimit> getMonthlyRequestLimits() {
        return this.monthlyRequestLimits;
    }

    public List<RequestLimit> getRequestLimits() {
        return requestLimits;
    }

    public RequestLimit getRequestLimit() {
        LocalDateTime now = LocalDateTime.now();
        Optional<RequestLimit> optionalRequestLimit = this.requestLimits.stream()
                .filter(rl -> rl.getTimeWindow().compareTo(Duration.between(rl.getCreatedAt(), now)) >= 0)
                .findFirst();

        return optionalRequestLimit.orElse(null);
    }
}
