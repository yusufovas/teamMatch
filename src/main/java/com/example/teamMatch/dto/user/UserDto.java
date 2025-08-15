package com.example.teamMatch.dto.user;

import java.util.List;
import java.util.UUID;

public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private List<String> roles;
    private List<String> skills;

    public UserDto(UUID id, String name, String email, List<String> roles, List<String> skills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.skills = skills;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getSkills() {
        return skills;
    }
}
