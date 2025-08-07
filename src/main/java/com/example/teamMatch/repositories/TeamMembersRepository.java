package com.example.teamMatch.repositories;

import com.example.teamMatch.model.TeamMemberId;
import com.example.teamMatch.model.TeamMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface TeamMembersRepository extends JpaRepository<TeamMembers, TeamMemberId> {
    List<TeamMembers> findByTeamId(UUID teamId);
    List<TeamMembers> findByUserId(UUID userId);
    boolean existsByTeamIdAndUserId(UUID teamId, UUID userId);
}
