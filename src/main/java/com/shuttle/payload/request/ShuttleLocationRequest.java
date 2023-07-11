package com.shuttle.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShuttleLocationRequest {
    @NotNull
    private double longitude1;
    @NotNull
    private double latitude1;
    @NotNull
    private double longitude2;
    @NotNull
    private double latitude2;
}
