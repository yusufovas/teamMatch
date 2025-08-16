package com.example.teamMatch.controllers;

import com.example.teamMatch.components.JwtUtils;
import com.example.teamMatch.dto.team.RespondRequestDto;
import com.example.teamMatch.dto.team.TeamRequestDto;
import com.example.teamMatch.services.team.teamJoinRequest.TeamJoinRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamJoinRequestController {

    private final TeamJoinRequestService requestService;
    private final JwtUtils jwtUtils;

    public TeamJoinRequestController(TeamJoinRequestService requestService, JwtUtils jwtUtils) {
        this.requestService = requestService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/{teamId}/requests")
    public ResponseEntity<TeamRequestDto> sendJoinRequest(
            @PathVariable UUID teamId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtils.getUserIdFromToken(token);

        TeamRequestDto requestDto = requestService.sendJoinRequest(teamId, userIdFromToken);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("/view-requests/{teamId}")
    public ResponseEntity<List<TeamRequestDto>> getRequestsForTeam(
            @PathVariable UUID teamId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtils.getUserIdFromToken(token);
        return ResponseEntity.ok(requestService.getRequestsForTeam(teamId, userIdFromToken));
    }

    @PostMapping("/{requestId}/respond")
    public ResponseEntity<TeamRequestDto> respondToRequest(
            @PathVariable UUID requestId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RespondRequestDto body
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID ownerId = jwtUtils.getUserIdFromToken(token);

        TeamRequestDto responseDto = requestService.respondToRequest(requestId, ownerId, body.getUserStatus());

        return ResponseEntity.ok(responseDto);
    }

    public static class ResponseBody {
        public String userStatus;
    }
}