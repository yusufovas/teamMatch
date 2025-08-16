package com.example.teamMatch.services.team;

import com.example.teamMatch.dto.role.RoleIdDto;
import com.example.teamMatch.dto.skill.SkillIdDto;
import com.example.teamMatch.dto.team.TeamDto;
import com.example.teamMatch.dto.user.UserDto;
import com.example.teamMatch.dto.user.UserIdDto;
import com.example.teamMatch.exception.AlreadyExistsException;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.exception.ValidationException;
import com.example.teamMatch.model.Team;
import com.example.teamMatch.enums.TeamStatusEnum;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<TeamDto> createTeam(String teamName, String description, TeamStatusEnum teamStatus, Users ownerId) {

        if (teamRepository.existsByName(teamName)) {
            throw new AlreadyExistsException("Team name already taken.");
        }

        Team team = new Team();
        team.setName(teamName);
        team.setDescription(description);
        team.setStatus(teamStatus);
        team.setOwner(ownerId);

        Team savedTeam = teamRepository.save(team);
        Users owner = savedTeam.getOwner();

        UserDto ownerDto = new UserDto(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getRoles().stream()
                        .map(role -> new RoleIdDto(role.getId(), role.getName()))
                        .toList(),
                owner.getSkills().stream()
                        .map(skill -> new SkillIdDto(skill.getId(), skill.getTitle()))
                        .toList(),
                owner.getTeams().stream()
                        .map(Team::getName)
                        .toList()
        );

        TeamDto dto = new TeamDto(
                savedTeam.getId(),
                savedTeam.getName(),
                savedTeam.getDescription(),
                savedTeam.getStatus(),
                new UserIdDto(savedTeam.getOwner().getId(), savedTeam.getOwner().getName(), savedTeam.getOwner().getEmail())
        );

        return ResponseEntity.ok(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeTeam(UUID teamId, UUID ownerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found with the id " + teamId));

        UUID owner = team.getOwner().getId();

        if(!owner.equals(ownerId)) {
            throw new ValidationException("You are not the owner of the team " + team.getName());
        }

        teamRepository.delete(team);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<TeamDto>> findTeamByStatus(TeamStatusEnum teamStatus) {
        List<Team> teams = teamRepository.findByStatus(teamStatus);

        List<TeamDto> teamDTOs = teams.stream()
                .map(t -> new TeamDto(
                        t.getId(),
                        t.getName(),
                        t.getDescription(),
                        t.getStatus(),
                        new UserIdDto(t.getOwner().getId(), t.getOwner().getName(), t.getOwner().getEmail())
                ))
                .toList();

        return ResponseEntity.ok(teamDTOs);
    }

}