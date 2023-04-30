package com.nicele08.notifyme.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public class MonthlyRequestLimitRequestBody {

    @NotNull(message = "Max requests cannot be null")
    @Min(value = 1, message = "Max requests must be greater than or equal to 1")
    @Schema(example = "100", description = "Maximum number of requests allowed for the client in the specified month")
    private Integer maxRequests;

    @NotNull(message = "Month cannot be null")
    @Schema(example = "2021-08-01", description = "Month for the request limit, expressed in the format 'yyyy-MM-dd'")
    private LocalDate month;

    @NotNull(message = "Client ID cannot be null")
    @Schema(example = "1", description = "ID of the client")
    private Long clientId;

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
