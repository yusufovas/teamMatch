package com.example.teamMatch.controllers;

import com.example.teamMatch.components.JwtUtils;
import com.example.teamMatch.dto.team.CreateTeamDto;
import com.example.teamMatch.dto.team.TeamDto;
import com.example.teamMatch.enums.TeamStatusEnum;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.UserRepository;
import com.example.teamMatch.services.team.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public TeamController(TeamService teamService, JwtUtils jwtUtils, UserRepository userRepository) {
        this.teamService = teamService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(
            @RequestBody CreateTeamDto request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        UUID ownerId = jwtUtils.getUserIdFromToken(token);

        Users owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("User with this id wasn't found " + ownerId));

        return teamService.createTeam(
                request.getName(),
                request.getDescription(),
                request.getStatus(),
                owner
        );
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Map<String, String>> removeTeam(@PathVariable UUID teamId,
                                          @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        UUID ownerId = jwtUtils.getUserIdFromToken(token);

        teamService.removeTeam(teamId, ownerId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Team deleted");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TeamDto>> findTeamsByStatus(@PathVariable TeamStatusEnum status) {
        return teamService.findTeamByStatus(status);
    }
}
