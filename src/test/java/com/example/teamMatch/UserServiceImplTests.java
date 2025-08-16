package com.example.teamMatch;

import com.example.teamMatch.dto.user.UserResponseDto;
import com.example.teamMatch.exception.AlreadyExistsException;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.RolesRepository;
import com.example.teamMatch.repositories.SkillsRepository;
import com.example.teamMatch.repositories.UserRepository;
import com.example.teamMatch.services.users.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private SkillsRepository skillsRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private Users existingUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingUser = new Users();
        existingUser.setId(UUID.randomUUID());
        existingUser.setName("Test User");
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("encodedPassword");
        existingUser.setRoles(new HashSet<>());
        existingUser.setSkills(new HashSet<>());
    }

    @Test
    void addUser_shouldSaveUser() {
        UserResponseDto dto = new UserResponseDto();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setPassword("password");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenAnswer(i -> i.getArguments()[0]);

        Users saved = userService.addUser(dto);

        assertEquals("John", saved.getName());
        assertEquals("john@example.com", saved.getEmail());
        assertEquals("encodedPassword", saved.getPassword());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void addUser_shouldThrowIfEmailExists() {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail("exists@example.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.addUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_shouldCallRepository() {
        UUID userId = existingUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void deleteUser_shouldThrowIfNotFound() {
        UUID randomId = UUID.randomUUID();
        when(userRepository.findById(randomId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(randomId));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void assignRoleToUser_shouldAssignRoles() {
        UUID userId = existingUser.getId();
        Roles role = new Roles();
        role.setId(UUID.randomUUID());
        role.setName("BACKEND ENGINEER");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(rolesRepository.findByNameIn(List.of("BACKEND ENGINEER"))).thenReturn(List.of(role));
        when(userRepository.save(any(Users.class))).thenReturn(existingUser);

        ResponseEntity<String> response = userService.assignRoleToUser(userId, List.of("BACKEND ENGINEER"));

        assertEquals("Roles assigned successfully", response.getBody());
        assertTrue(existingUser.getRoles().contains(role));
    }

    @Test
    void removeRoleFromUser_shouldRemoveRole() {
        UUID userId = existingUser.getId();
        Roles role = new Roles();
        role.setId(UUID.randomUUID());
        existingUser.getRoles().add(role);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(rolesRepository.findById(role.getId())).thenReturn(Optional.of(role));

        ResponseEntity<String> response = userService.removeRoleFromUser(userId, role.getId());

        assertEquals("Role removed successfully", response.getBody());
        assertFalse(existingUser.getRoles().contains(role));
        verify(userRepository).save(existingUser);
    }

    @Test
    void removeRoleFromUser_shouldReturnBadRequestIfUserDoesNotHaveRole() {
        UUID userId = existingUser.getId();
        Roles role = new Roles();
        role.setId(UUID.randomUUID());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(rolesRepository.findById(role.getId())).thenReturn(Optional.of(role));

        ResponseEntity<String> response = userService.removeRoleFromUser(userId, role.getId());
        assertEquals("User did not have this role", response.getBody());
    }
}
