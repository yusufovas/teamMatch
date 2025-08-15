package com.example.teamMatch.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ROLES")
public class Roles {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
