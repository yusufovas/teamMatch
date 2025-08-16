package com.example.teamMatch.repositories;

import com.example.teamMatch.model.TeamJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, UUID> {
    List<TeamJoinRequest> findByTeam_Id(UUID teamId);
    List<TeamJoinRequest> findByTeam_IdAndStatus(UUID teamId, TeamJoinRequest.RequestStatus status);

    boolean existsByTeam_IdAndUser_IdAndStatus(UUID teamId, UUID userId, TeamJoinRequest.RequestStatus requestStatus);
}
