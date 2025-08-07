package com.example.teamMatch.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Timezones {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "utc_offset", nullable = false)
    private String utcOffset;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }
}
