package com.example.teamMatch.model.join;


import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Team;
import com.example.teamMatch.model.Users;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TEAM_MEMBERS")
@IdClass(TeamMemberId.class)
public class TeamMembers {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_member_roles",
            joinColumns = {
                    @JoinColumn(name = "team_id"),
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    @Column(nullable = false)
    private boolean approved;

    @Column(name = "JOINED_AT", nullable = false)
    private LocalDateTime joinedAt;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<Roles> getRole() {
        return roles;
    }

    public void setRole(Set<Roles> role) {
        this.roles = role;
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
