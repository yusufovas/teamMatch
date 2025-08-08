package com.example.teamMatch.services.userService;

import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Users;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Users register(UserDto userDto);
    String login(String email, String password);
    boolean updatePassword(UUID userId, String oldPassword, String newPassword);

    Users addUser(UserDto userDto);
    Users updateUser(UUID userId, String oldName, String newName, String oldEmail, String newEmail);
    Users deleteUser(UUID userId);
    List<Users> searchUsersBySkill(String skillName);
    List<Roles> searchUsersByRole(String roleName);

    Users findByEmail(String email);
    Users findByName(String name);
    Users findById(UUID id);
    boolean existsByEmail(String email);
    List<Users> getAllUsers();

    boolean assignRoleToUser(UUID userId, UUID roleId);
    boolean removeRoleFromUser(UUID userId, UUID roleId);
    List<Roles> getUserRoles(UUID userId);
    String assignSkillToUser(UUID userId, UUID roleId);
    String removeSkillFromUser(UUID userId, UUID roleId);
    List<Skills> getUserSkills(UUID userId);

}
