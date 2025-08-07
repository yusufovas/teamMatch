package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findByStatus(String status);
    List<Team> findByOwnerId(UUID ownerId);
}
