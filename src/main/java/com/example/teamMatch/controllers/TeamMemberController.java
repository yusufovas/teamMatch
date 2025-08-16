package com.example.teamMatch.controllers;

import com.example.teamMatch.dto.team.TeamMemberDto;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.services.team.teamMembers.TeamMembersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/team-members")
public class TeamMemberController {

    private final TeamMembersService teamMemberService;

    public TeamMemberController(TeamMembersService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<TeamMemberDto>> getMembersByTeam(@PathVariable UUID teamId) {
        try {
            List<TeamMemberDto> members = teamMemberService.getMembersByTeamId(teamId);
            if (members.isEmpty()) {
                return ResponseEntity.ok(members);
            }
            return ResponseEntity.ok(members);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TeamMemberDto>> getTeamsByUser(@PathVariable UUID userId) {
        List<TeamMemberDto> teams = teamMemberService.getTeamsByUserId(userId);

        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teams);
    }

}
