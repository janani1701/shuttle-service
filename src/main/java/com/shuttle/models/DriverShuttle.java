package com.shuttle.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class DriverShuttle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double longitude1;
    private double latitude1;
    private double latitude2;
    private double longitude2;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
