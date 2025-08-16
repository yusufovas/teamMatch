package com.example.teamMatch.dto.role;

import java.util.UUID;

public class RoleIdDto {
    private UUID roleId;
    private String name;

    public RoleIdDto(UUID id, String name) {
        this.roleId = id;
        this.name = name;
    }

    public UUID getRoleId() {
        return roleId;
    }
    public String getName() { return name; }
}
