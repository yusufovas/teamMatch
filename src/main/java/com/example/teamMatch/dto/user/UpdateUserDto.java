package com.example.teamMatch.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.UUID;
public class UpdateUserDto {

    private UUID id;

    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    public UpdateUserDto(UUID id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UUID getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
