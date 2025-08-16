package com.example.teamMatch.repositories;

import com.example.teamMatch.enums.TeamStatusEnum;
import com.example.teamMatch.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    boolean existsByName(String name);
    Optional<Team> findByOwnerId(UUID ownerId);
    List<Team> findByStatus(TeamStatusEnum status);

}
