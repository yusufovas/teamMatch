package com.example.teamMatch.repositories;

import com.example.teamMatch.model.UserSkillId;
import com.example.teamMatch.model.UserSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface UserSkillsRepository extends JpaRepository<UserSkills, UserSkillId> {
    List<UserSkills> findByUserId(UUID userId);
    boolean existsByUserIdAndSkillId(UUID userId, UUID skillId);
}
