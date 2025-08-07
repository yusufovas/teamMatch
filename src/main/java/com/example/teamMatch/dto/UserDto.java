package com.example.teamMatch.dto;

import com.example.teamMatch.model.Timezones;
import com.example.teamMatch.model.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserDto {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String timezoneName;

    public UserDto() { }

    public UserDto(Users user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.timezoneName = user.getTimezone().getName();
    }

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

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }
}
