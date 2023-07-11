package com.shuttle.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class StudentShuttle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String address;

    private String Status;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
