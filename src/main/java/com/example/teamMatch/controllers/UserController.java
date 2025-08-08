package com.example.teamMatch.controllers;

import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.dto.UserResponseDto;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.services.userService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserDto userDto) {

        Users savedUser = userService.addUser(userDto);

        UserResponseDto responseDto = new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        return ResponseEntity.ok(responseDto);
    }

}
