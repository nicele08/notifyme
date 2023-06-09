package com.nicele08.notifyme.model;

import com.nicele08.notifyme.entity.RequestorType;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class RequestRequestBody {
    @NotNull(message = "type cannot be null")
    @Schema(example = "CLIENT", description = "Type of the requestor")
    private RequestorType type;

    public RequestorType getType() {
        return type;
    }

    public void setType(RequestorType type) {
        this.type = type;
    }

}
