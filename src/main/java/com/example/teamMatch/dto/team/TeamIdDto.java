package com.example.teamMatch.dto.team;

import com.example.teamMatch.enums.TeamStatusEnum;

import java.util.UUID;

public class TeamIdDto {
    private UUID id;
    private String name;
    private String description;
    private TeamStatusEnum status;

    public TeamIdDto(UUID id, String name, String description, TeamStatusEnum status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TeamStatusEnum getStatus() {
        return status;
    }
}
