package com.example.teamMatch.controllers;

import com.example.teamMatch.components.JwtUtils;
import com.example.teamMatch.dto.AddUserResponseDto;
import com.example.teamMatch.dto.UpdateUserDto;
import com.example.teamMatch.dto.UpdateUserResponseDto;
import com.example.teamMatch.dto.UserDto;
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
        return userService.getAllUsers()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

    }

    @PostMapping
    public ResponseEntity<AddUserResponseDto> addUser(@Valid @RequestBody UserDto userDto) {

        Users savedUser = userService.addUser(userDto);

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
}
