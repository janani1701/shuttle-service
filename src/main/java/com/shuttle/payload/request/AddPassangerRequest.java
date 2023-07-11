package com.shuttle.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddPassangerRequest {
    @NotBlank
    private String studentUsername;

    @NotBlank
    private String address;
}
