package com.example.teamMatch.services.team.teamJoinRequest;

import com.example.teamMatch.dto.team.TeamRequestDto;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.exception.ValidationException;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Team;
import com.example.teamMatch.model.TeamJoinRequest;
import com.example.teamMatch.model.join.TeamMembers;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.TeamJoinRequestRepository;
import com.example.teamMatch.repositories.TeamMembersRepository;
import com.example.teamMatch.repositories.TeamRepository;
import com.example.teamMatch.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TeamJoinRequestService {

    private final TeamJoinRequestRepository requestRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMembersRepository teamMembersRepository;

    public TeamJoinRequestService(TeamJoinRequestRepository requestRepository,
                                  TeamRepository teamRepository,
                                  UserRepository userRepository,
                                  TeamMembersRepository teamMembersRepository) {
        this.requestRepository = requestRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamMembersRepository = teamMembersRepository;
    }

    @Transactional
    public TeamRequestDto sendJoinRequest(UUID teamId, UUID userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found"));

        if (requestRepository.existsByTeam_IdAndUser_IdAndStatus(teamId, userId, TeamJoinRequest.RequestStatus.PENDING)) {
            throw new ValidationException("A pending request already exists");
        }

        TeamJoinRequest request = new TeamJoinRequest();
        request.setTeam(team);
        request.setUser(user);
        request.setStatus(TeamJoinRequest.RequestStatus.PENDING);

        TeamJoinRequest savedRequest = requestRepository.save(request);

        return new TeamRequestDto(
                savedRequest.getId(),
                team.getId(),
                user.getId(),
                user.getName(),
                user.getEmail(),
                savedRequest.getStatus().name(),
                savedRequest.getCreatedAt().toString()
        );
    }

    @Transactional
    public TeamRequestDto respondToRequest(UUID requestId, UUID ownerId, String userStatus) {
        TeamJoinRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found"));

        if (!request.getTeam().getOwner().getId().equals(ownerId)) {
            throw new ValidationException("Only team owner can respond to requests");
        }

        if ("approve".equalsIgnoreCase(userStatus)) {
            request.setStatus(TeamJoinRequest.RequestStatus.APPROVED);

            TeamMembers member = new TeamMembers();
            member.setTeam(request.getTeam());
            member.setUser(request.getUser());
            member.setRole(request.getUser().getRoles());
            member.setApproved(true);
            member.setJoinedAt(LocalDateTime.now());
            teamMembersRepository.save(member);

        } else if ("reject".equalsIgnoreCase(userStatus)) {
            request.setStatus(TeamJoinRequest.RequestStatus.REJECTED);
        } else {
            throw new ValidationException("Invalid status. Use 'approve' or 'reject'");
        }

        TeamJoinRequest savedRequest = requestRepository.save(request);

        return new TeamRequestDto(
                savedRequest.getId(),
                savedRequest.getTeam().getId(),
                savedRequest.getUser().getId(),
                savedRequest.getUser().getName(),
                savedRequest.getUser().getEmail(),
                savedRequest.getStatus().name(),
                savedRequest.getCreatedAt().toString()
        );
    }

    public List<TeamRequestDto> getRequestsForTeam(UUID teamId, UUID ownerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found"));

        if (!team.getOwner().getId().equals(ownerId)) {
            throw new ValidationException("Only team owner can view requests");
        }

        return requestRepository.findByTeam_Id(teamId).stream()
                .map(r -> new TeamRequestDto(
                        r.getId(),
                        r.getTeam().getId(),
                        r.getUser().getId(),
                        r.getUser().getName(),
                        r.getUser().getEmail(),
                        r.getStatus().toString(),
                        r.getCreatedAt().toString()
                ))
                .toList();
    }
}