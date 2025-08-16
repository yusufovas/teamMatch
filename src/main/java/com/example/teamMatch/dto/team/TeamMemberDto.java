package com.example.teamMatch.dto.team;

import java.time.LocalDateTime;
import java.util.UUID;

public class TeamMemberDto {
    private UUID teamId;
    private UUID userId;
    private String userName;
    private String roleName;
    private boolean approved;
    private LocalDateTime joinedAt;

    public TeamMemberDto(UUID teamId, UUID userId, String userName, String roleName, boolean approved, LocalDateTime joinedAt) {
        this.teamId = teamId;
        this.userId = userId;
        this.userName = userName;
        this.roleName = roleName;
        this.approved = approved;
        this.joinedAt = joinedAt;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
