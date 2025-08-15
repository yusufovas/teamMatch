package com.example.teamMatch.model.join;


import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Team;
import com.example.teamMatch.model.Users;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Roles role;

    @Column(nullable = false)
    private boolean approved;

    @Column(name = "JOINED_AT", nullable = false)
    private LocalDateTime joinedAt;
}
