package com.example.teamMatch.services.users;

import com.example.teamMatch.dto.role.RoleIdDto;
import com.example.teamMatch.dto.skill.SkillIdDto;
import com.example.teamMatch.dto.team.TeamIdDto;
import com.example.teamMatch.dto.user.UpdateUserDto;
import com.example.teamMatch.dto.user.UserResponseDto;
import com.example.teamMatch.dto.user.UserDto;
import com.example.teamMatch.exception.NotFoundException;
import com.example.teamMatch.exception.ValidationException;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Team;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.RolesRepository;
import com.example.teamMatch.repositories.SkillsRepository;
import com.example.teamMatch.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final SkillsRepository skillsRepository;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository, SkillsRepository skillsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.skillsRepository = skillsRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> new RoleIdDto(role.getId(), role.getName()))
                                .toList(),
                        user.getSkills().stream()
                                .map(skill -> new SkillIdDto(skill.getId(), skill.getTitle()))
                                .toList(),
                        user.getTeams().stream()
                                .map(Team::getName)
                                .toList()
                ))
                .toList();
    }

    @Override
    @Transactional
    public Users addUser(UserResponseDto userResponseDto) {
       if(userRepository.existsByEmail(userResponseDto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setName(userResponseDto.getName());
        user.setEmail(userResponseDto.getEmail());
        user.setPassword(passwordEncoder.encode(userResponseDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public Users updateUser(UUID userIdFromToken, UpdateUserDto updateUserDto) {

        Users user = userRepository.findById(userIdFromToken)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.findByEmailIgnoreCase(updateUserDto.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userIdFromToken)) {
                        throw new NotFoundException("Ids do not match");
                    }
                });


        if(!passwordEncoder.matches(updateUserDto.getPassword(), user.getPassword())) {
            throw new ValidationException("Old password is incorrect");
        }

        if(updateUserDto.getName() != null && !updateUserDto.getName().isBlank()) {
            user.setName(updateUserDto.getName());
        }

        if(updateUserDto.getEmail() != null && !updateUserDto.getEmail().isBlank()) {
            user.setEmail(updateUserDto.getEmail());
        }

        if(updateUserDto.getPassword() != null || !updateUserDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getId().equals(userId)) {
            throw new NotFoundException("Ids do not match");
        };

        userRepository.delete(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        Users user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User with this email not found " + email));

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> new RoleIdDto(role.getId(), role.getName()))
                        .toList(),
                user.getSkills().stream()
                        .map(skill -> new SkillIdDto(skill.getId(), skill.getTitle()))
                        .toList(),
                user.getTeams().stream()
                        .map(Team::getName)
                        .toList()
        );
    }

    @Override
    public List<UserDto> findByName(String name) {
        List<Users> users = userRepository.findByNameIgnoreCase(name);

        if (users.isEmpty()) {
            throw new NotFoundException("User with this name not found " + name);
        }

        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> new RoleIdDto(role.getId(), role.getName()))
                                .toList(),
                        user.getSkills().stream()
                                .map(skill -> new SkillIdDto(skill.getId(), skill.getTitle()))
                                        .toList(),
                        user.getTeams().stream()
                                .map(Team::getName)
                                .toList()))
                .toList();
    }

    @Override
    public Users findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with this id not found " + id));
    }

    @Override
    public ResponseEntity<String> assignRoleToUser(UUID userId, List<String> roleTitles) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with this id does not exist " + userId));

        List<String> upperCaseTitles = roleTitles.stream()
                .map(String::toUpperCase)
                .distinct()
                .toList();

        List<Roles> roles = rolesRepository.findByNameIn(upperCaseTitles);

        if (roles.size() != upperCaseTitles.size()) {
            return ResponseEntity.badRequest().body("Some roles were not found");
        }

        user.getRoles().addAll(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Roles assigned successfully");
    }

    @Override
    public ResponseEntity<String> removeRoleFromUser(UUID userId, UUID roleId) {
        Optional<Users> userOpt = userRepository.findById(userId);
        Optional<Roles> roleOpt = rolesRepository.findById(roleId);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("User not found " + userId);
        }
        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Role not found");
        }

        Users user = userOpt.get();
        boolean removed = user.getRoles().remove(roleOpt.get());

        if (!removed) {
            return ResponseEntity.badRequest().body("User did not have this role");
        }

        userRepository.save(user);
        return ResponseEntity.ok("Role removed successfully");
    }

    @Override
    public List<String> getUserRoles(UUID userId) {
        return userRepository.findByIdWithRoles(userId)
                .map(user -> user.getRoles()
                        .stream()
                        .map(Roles::getName)
                        .toList())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    @Override
    public ResponseEntity<String> assignSkillToUser(UUID userId, List<String> skillTitles) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with this id does not exist " + userId));

        List<String> upperCaseTitles = skillTitles.stream()
                .map(String::toUpperCase)
                .distinct()
                .toList();

        List<Skills> skills = skillsRepository.findByTitleIn(upperCaseTitles);

        if (skills.size() != upperCaseTitles.size()) {
            return ResponseEntity.badRequest().body("Some roles were not found");
        }

        user.getSkills().addAll(skills);
        userRepository.save(user);

        return ResponseEntity.ok("Skills assigned successfully");
    }

    @Override
    public ResponseEntity<String> removeSkillFromUser(UUID userId, UUID skillId) {
        Optional<Users> userOpt = userRepository.findById(userId);
        Optional<Skills> skillsOpt = skillsRepository.findById(skillId);

        if (userOpt.isEmpty()) {
            throw new NotFoundException("User not found " + userId);
        }
        if (skillsOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Skill not found");
        }

        Users user = userOpt.get();
        boolean removed = user.getSkills().remove(skillsOpt.get());

        if (!removed) {
            return ResponseEntity.badRequest().body("User did not have this skill");
        }

        userRepository.save(user);
        return ResponseEntity.ok("Skill removed successfully");
    }

    @Override
    public List<String> getUserSkills(UUID userId) {
        return userRepository.findByIdWithSkills(userId)
                .map(user -> user.getSkills()
                        .stream()
                        .map(Skills::getTitle)
                        .toList())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
    }
}
