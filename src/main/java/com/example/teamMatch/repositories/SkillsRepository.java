package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, UUID> {
    List<Skills> findByTitleIn(List<String> upperCaseTitles);

    Optional<Skills> findByTitle(String skillTitle);

    boolean existsByTitle(String skillTitle);
}
