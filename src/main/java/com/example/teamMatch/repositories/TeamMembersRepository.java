package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Users;
import com.example.teamMatch.model.join.TeamMemberId;
import com.example.teamMatch.model.join.TeamMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TeamMembersRepository extends JpaRepository<TeamMembers, TeamMemberId> {
    List<TeamMembers> findByTeam_Id(UUID teamId);
    List<TeamMembers> findByUser_Id(UUID userId);
}
