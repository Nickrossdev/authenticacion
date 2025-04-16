package com.nickrossdev.authentication.domain.authentication.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String description;
}
