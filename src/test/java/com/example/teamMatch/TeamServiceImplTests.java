package com.example.teamMatch;

import com.example.teamMatch.dto.role.RoleIdDto;
import com.example.teamMatch.dto.skill.SkillIdDto;
import com.example.teamMatch.dto.team.TeamDto;
import com.example.teamMatch.dto.user.UserIdDto;
import com.example.teamMatch.enums.TeamStatusEnum;
import com.example.teamMatch.exception.AlreadyExistsException;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.exception.ValidationException;
import com.example.teamMatch.model.Team;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.TeamRepository;
import com.example.teamMatch.services.team.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Users owner;
    private Team team;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        owner = new Users();
        owner.setId(UUID.randomUUID());
        owner.setName("John Doe");
        owner.setEmail("john@example.com");

        team = new Team();
        team.setId(UUID.randomUUID());
        team.setName("Dev Team");
        team.setDescription("Development Team");
        team.setStatus(TeamStatusEnum.OPEN);
        team.setOwner(owner);
    }

    @Test
    void createTeam_Success() {
        when(teamRepository.existsByName("Dev Team")).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        ResponseEntity<TeamDto> response = teamService.createTeam(
                "Dev Team",
                "Development Team",
                TeamStatusEnum.OPEN,
                owner
        );

        assertNotNull(response);
        assertEquals("Dev Team", response.getBody().getName());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void createTeam_AlreadyExists() {
        when(teamRepository.existsByName("Dev Team")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () ->
                teamService.createTeam("Dev Team", "Development Team", TeamStatusEnum.OPEN, owner));
        verify(teamRepository, never()).save(any());
    }

    @Test
    void removeTeam_Success() {
        when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

        ResponseEntity<Void> response = teamService.removeTeam(team.getId(), owner.getId());
        assertEquals(204, response.getStatusCodeValue());
        verify(teamRepository, times(1)).delete(team);
    }

    @Test
    void removeTeam_NotOwner() {
        when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));
        UUID anotherUserId = UUID.randomUUID();

        assertThrows(ValidationException.class, () ->
                teamService.removeTeam(team.getId(), anotherUserId));
        verify(teamRepository, never()).delete(team);
    }

    @Test
    void removeTeam_NotFound() {
        when(teamRepository.findById(team.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                teamService.removeTeam(team.getId(), owner.getId()));
        verify(teamRepository, never()).delete(any());
    }

    @Test
    void findTeamByStatus_Success() {
        when(teamRepository.findByStatus(TeamStatusEnum.OPEN)).thenReturn(List.of(team));

        ResponseEntity<List<TeamDto>> response = teamService.findTeamByStatus(TeamStatusEnum.OPEN);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(team.getName(), response.getBody().get(0).getName());
        verify(teamRepository, times(1)).findByStatus(TeamStatusEnum.OPEN);
    }

    @Test
    void findTeamByStatus_Empty() {
        when(teamRepository.findByStatus(TeamStatusEnum.CLOSED)).thenReturn(Collections.emptyList());

        ResponseEntity<List<TeamDto>> response = teamService.findTeamByStatus(TeamStatusEnum.CLOSED);
        assertTrue(response.getBody().isEmpty());
    }
}
