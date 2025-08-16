package com.example.teamMatch.dto.user;

import com.example.teamMatch.dto.role.RoleIdDto;
import com.example.teamMatch.dto.skill.SkillIdDto;
import com.example.teamMatch.dto.team.TeamIdDto;

import java.util.List;
import java.util.UUID;

public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private List<RoleIdDto> roles;
    private List<SkillIdDto> skills;
    private List<String> teams;

    public UserDto(UUID id, String name, String email, List<RoleIdDto> roles, List<SkillIdDto> skills, List<String> teams) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.skills = skills;
        this.teams = teams;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RoleIdDto> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public List<SkillIdDto> getSkills() {
        return skills;
    }

    public List<String> getTeams() {
        return teams;
    }
}
