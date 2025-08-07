package com.example.teamMatch.repositories;

import com.example.teamMatch.model.TeamRequirements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface TeamRequirementsRepository extends JpaRepository<TeamRequirements, UUID> {
    List<TeamRequirements> findByTeamId(UUID teamId);
}
