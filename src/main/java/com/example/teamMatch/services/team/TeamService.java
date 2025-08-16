package com.example.teamMatch.services.team;

import com.example.teamMatch.dto.team.TeamDto;
import com.example.teamMatch.enums.TeamStatusEnum;
import com.example.teamMatch.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    ResponseEntity<TeamDto> createTeam(String teamName, String description, TeamStatusEnum teamStatus, Users ownerId);

    ResponseEntity<Void> removeTeam(UUID teamId, UUID ownerId);

    ResponseEntity<List<TeamDto>> findTeamByStatus(TeamStatusEnum teamStatus);
}
