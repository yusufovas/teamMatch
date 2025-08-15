package com.example.teamMatch.services.users;

import com.example.teamMatch.dto.user.UpdateUserDto;
import com.example.teamMatch.dto.user.UserDto;
import com.example.teamMatch.dto.user.UserResponseDto;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    List<Users> getAllUsers();
    Users addUser(UserResponseDto userResponseDto);
    Users updateUser(UUID userId, UpdateUserDto updateUserDto);
    void deleteUser(UUID userId);

    UserDto findByEmail(String email);
    List<UserDto> findByName(String name);
    Users findById(UUID id);

    ResponseEntity<String> assignRoleToUser(UUID userId, List<String> roleTitles);
    ResponseEntity<String> removeRoleFromUser(UUID userId, UUID roleId);
    List<String> getUserRoles(UUID userId);
    ResponseEntity<String> assignSkillToUser(UUID userId, List<String> skillTitles);
    ResponseEntity<String> removeSkillFromUser(UUID userId, UUID skillId);
    List<String> getUserSkills(UUID userId);

}
