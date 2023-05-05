package com.nicele08.notifyme.model;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public class MonthlyRequestLimitRequestBody {

    @NotNull(message = "Max requests cannot be null")
    @Min(value = 1, message = "Max requests must be greater than or equal to 1")
    @Schema(example = "100", description = "Maximum number of requests allowed for the client in the specified month")
    private Integer maxRequests;

    @NotNull(message = "Client ID cannot be null")
    @Schema(example = "1", description = "ID of the client")
    private Long clientId;

    public Integer getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(Integer maxRequests) {
        this.maxRequests = maxRequests;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
