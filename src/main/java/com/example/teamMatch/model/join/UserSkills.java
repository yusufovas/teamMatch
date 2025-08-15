package com.example.teamMatch.model.join;

import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Users;
import jakarta.persistence.*;

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

}
