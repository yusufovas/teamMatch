package com.example.teamMatch.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_skills")
@IdClass(UserSkillId.class)
public class UserSkills {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skills skill;

    // Optional: add extra fields like proficiency or years if needed
}
