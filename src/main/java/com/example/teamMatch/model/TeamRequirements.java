package com.example.teamMatch.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TEAM_REQUIREMENTS")
public class TeamRequirements {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "MIN_COUNT", nullable = false)
    private int minCount;

    @Column(name = "MAX_COUNT", nullable = false)
    private int maxCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Roles role;
}
