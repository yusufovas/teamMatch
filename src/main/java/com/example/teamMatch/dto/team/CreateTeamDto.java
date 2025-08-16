package com.example.teamMatch.dto.team;

import com.example.teamMatch.enums.TeamStatusEnum;

public class CreateTeamDto {
    private String name;
    private String description;
    private TeamStatusEnum status;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TeamStatusEnum getStatus() { return status; }
    public void setStatus(TeamStatusEnum status) { this.status = status; }
}
