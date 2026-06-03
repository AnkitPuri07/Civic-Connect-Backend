package com.ankit.civicconnectbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name must be filled")
    @Column(nullable = false)
    private String adminName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email must be filled")
    @Column(nullable = false, unique = true)
    private String adminEmail;

    @NotBlank(message = "password must be filled")
    @Column(nullable = false)
    private String adminPassword;
}