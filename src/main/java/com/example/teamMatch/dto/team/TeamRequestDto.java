package com.example.teamMatch.dto.team;

import java.time.LocalDateTime;
import java.util.UUID;

public class TeamRequestDto {
    private UUID requestId;
    private UUID teamId;
    private UUID userId;
    private String userName;
    private String userEmail;
    private String status;
    private String createdAt;

    public TeamRequestDto(UUID requestId, UUID teamId, UUID userId, String userName, String userEmail, String status, String createdAt) {
        this.requestId = requestId;
        this.teamId = teamId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
