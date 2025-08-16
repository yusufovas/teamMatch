package com.example.teamMatch.model;


import com.example.teamMatch.enums.TeamStatusEnum;
import com.example.teamMatch.model.join.TeamMembers;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TEAMS")
public class Team {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private Users owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamStatusEnum status;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamRequirements> requirements = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMembers> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamJoinRequest> requests = new ArrayList<>();

    public Team() {
    }

    public Team(String name, String description, Users owner, TeamStatusEnum status) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.status = status;
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

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public TeamStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TeamStatusEnum status) {
        this.status = status;
    }

    public List<TeamRequirements> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<TeamRequirements> requirements) {
        this.requirements = requirements;
    }

    public List<TeamMembers> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMembers> members) {
        this.members = members;
    }
}
