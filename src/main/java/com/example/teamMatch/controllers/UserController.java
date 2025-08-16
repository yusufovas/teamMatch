package com.example.teamMatch.controllers;

import com.example.teamMatch.components.JwtUtils;
import com.example.teamMatch.dto.role.RoleIdDto;
import com.example.teamMatch.dto.skill.SkillIdDto;
import com.example.teamMatch.dto.user.*;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.services.users.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtility;

    public UserController(UserService userService, JwtUtils jwtUtility) {
        this.userService = userService;
        this.jwtUtility = jwtUtility;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<AddUserResponseDto> addUser(@Valid @RequestBody UserResponseDto userResponseDto) {

        Users savedUser = userService.addUser(userResponseDto);

        AddUserResponseDto responseDto = new AddUserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponseDto> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateUserDto updateUserDto) {

        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtility.getUserIdFromToken(token);

        Users updatedUser = userService.updateUser(userIdFromToken, updateUserDto);

        UpdateUserResponseDto responseDto = new UpdateUserResponseDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtility.getUserIdFromToken(token);

        userService.deleteUser(userIdFromToken);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<List<UserDto>> getUsersByName(@RequestParam String name) {
        List<UserDto> users = userService.findByName(name);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-user-roles")
    public ResponseEntity<List<String>> getUserRoles(@RequestParam UUID userId) {
        List<String> roles = userService.getUserRoles(userId);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/set-user-role")
    public ResponseEntity<String> setUserRoles(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<String> roleTitles
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtility.getUserIdFromToken(token);

        return userService.assignRoleToUser(userIdFromToken, roleTitles);
    }

    @DeleteMapping("/delete-user-role")
    public ResponseEntity<String> deleteUserRole(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RoleIdDto request) {

        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtility.getUserIdFromToken(token);

        return userService.removeRoleFromUser(userIdFromToken, request.getRoleId());
    }

    @GetMapping("/get-user-skill")
    public ResponseEntity<List<String>> getUserSkills(@RequestParam UUID userId) {
        List<String> skills = userService.getUserSkills(userId);
        return ResponseEntity.ok(skills);
    }

    @PostMapping("/set-user-skill")
    public ResponseEntity<String> setUserSkills(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<String> skillTitles
    ) {
        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtility.getUserIdFromToken(token);

        return userService.assignSkillToUser(userIdFromToken, skillTitles);
    }

    @DeleteMapping("/delete-user-skill")
    public ResponseEntity<String> deleteUserSkill(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody SkillIdDto request) {

        String token = authHeader.replace("Bearer ", "");
        UUID userIdFromToken = jwtUtility.getUserIdFromToken(token);

        return userService.removeSkillFromUser(userIdFromToken, request.getSkillId());
    }
}
