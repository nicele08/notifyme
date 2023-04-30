package com.nicele08.notifyme.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientRequestBody {
    @NotBlank(message = "Name cannot be blank")
    @Schema(example = "NiCele", description = "Name of the client")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Schema(example = "nicelestin08@gmail.com", description = "Email address of the client")
    private String email;

    @NotBlank(message = "API key cannot be blank")
    @Schema(example = "1234567890", description = "API key of the client")
    private String apiKey;

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
}
