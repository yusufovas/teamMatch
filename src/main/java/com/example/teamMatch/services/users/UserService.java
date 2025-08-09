package com.example.teamMatch.services.users;

import com.example.teamMatch.dto.UpdateUserDto;
import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Users;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<Users> getAllUsers();
    Users addUser(UserDto userDto);
    Users updateUser(UUID userId, UpdateUserDto updateUserDto);
    Users deleteUser(UUID userId);

    Users findByEmail(String email);
    Users findByName(String name);
    Users findById(UUID id);
    boolean existsByEmail(String email);

    boolean assignRoleToUser(UUID userId, UUID roleId);
    boolean removeRoleFromUser(UUID userId, UUID roleId);
    List<Roles> getUserRoles(UUID userId);
    String assignSkillToUser(UUID userId, UUID roleId);
    String removeSkillFromUser(UUID userId, UUID roleId);
    List<Skills> getUserSkills(UUID userId);

}
