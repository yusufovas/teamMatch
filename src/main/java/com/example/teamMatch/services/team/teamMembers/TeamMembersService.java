package com.example.teamMatch.services.team.teamMembers;

import com.example.teamMatch.dto.team.TeamMemberDto;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.exception.ValidationException;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.join.TeamMembers;
import com.example.teamMatch.repositories.TeamMembersRepository;
import com.example.teamMatch.repositories.TeamRepository;
import com.example.teamMatch.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamMembersService {

    private final TeamMembersRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;


    public TeamMembersService(TeamMembersRepository teamMemberRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<TeamMemberDto> getMembersByTeamId(UUID teamId) {
        if (teamId == null) {
            throw new ValidationException("Team ID cannot be null");
        }

        boolean exists = teamRepository.existsById(teamId);
        if (!exists) {
            throw new NotFoundException("Team with id " + teamId + " not found");
        }

        List<TeamMembers> members = teamMemberRepository.findByTeam_Id(teamId);

        return members.stream()
                .map(m -> new TeamMemberDto(
                        m.getTeam().getId(),
                        m.getUser().getId(),
                        m.getUser().getName(),
                        m.getUser().getEmail(),
                        m.isApproved(),
                        m.getJoinedAt()
                ))
                .toList();
    }


    @Transactional(readOnly = true)
    public List<TeamMemberDto> getTeamsByUserId(UUID userId) {
        List<TeamMembers> members = teamMemberRepository.findByUser_Id(userId);

        if (members.isEmpty()) {
            return Collections.emptyList();
        }

        return members.stream()
                .map(m -> new TeamMemberDto(
                        m.getTeam().getId(),
                        m.getUser().getId(),
                        m.getUser().getName(),
                        m.getRole().stream().map(Roles::getName).collect(Collectors.joining(", ")),
                        m.isApproved(),
                        m.getJoinedAt()
                ))
                .toList();
    }


}
