package com.example.teamMatch.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UpdateUserResponseDto {

    private UUID id;
    private String name;
    private String email;

    public UpdateUserResponseDto(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
