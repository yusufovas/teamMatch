package com.example.teamMatch.dto.team;

import com.example.teamMatch.dto.user.UserIdDto;
import com.example.teamMatch.enums.TeamStatusEnum;

import java.util.UUID;

public class TeamDto {
    private UUID id;
    private String name;
    private String description;
    private TeamStatusEnum status;
    private UserIdDto owner;

    public TeamDto() {
    }

    public TeamDto(UUID id, String name, String description, TeamStatusEnum status, UserIdDto owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.owner = owner;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeamStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TeamStatusEnum status) {
        this.status = status;
    }

    public UserIdDto getOwner() {
        return owner;
    }

    public void setOwner(UserIdDto owner) {
        this.owner = owner;
    }
}
