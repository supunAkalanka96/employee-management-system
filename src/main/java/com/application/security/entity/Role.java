package com.application.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<User> user;
}
