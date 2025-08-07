package com.example.teamMatch.controllers;

import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.services.userService.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

    }

    @PostMapping("/hell")
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        Users user = userService.addUser(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getTimezoneName()
        );

        return new UserDto(user);
    }

}
