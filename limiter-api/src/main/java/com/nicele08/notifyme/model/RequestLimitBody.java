package com.nicele08.notifyme.model;

import java.time.Duration;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public class RequestLimitBody {

    @NotNull(message = "Time window cannot be null")
    @Schema(example = "PT1H30M", description = "Time window for the request limit, expressed in the format 'PTnHnMnS', where n is a number of hours, minutes, or seconds. For example, 'PT1H30M' represents a time window of one hour and thirty minutes.")
    private Duration timeWindow;

    @NotNull(message = "Max requests cannot be null")
    @Min(value = 1, message = "Max requests must be greater than or equal to 1")
    @Schema(example = "100", description = "Maximum number of requests allowed for the client in the specified time window")
    private Integer maxRequests;

    @NotNull(message = "Client ID cannot be null")
    @Schema(example = "1", description = "ID of the client")
    private Long clientId;

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
