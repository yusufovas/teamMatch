package com.example.teamMatch.services.users;

import com.example.teamMatch.dto.user.UpdateUserDto;
import com.example.teamMatch.dto.user.UserResponseDto;
import com.example.teamMatch.dto.user.UserDto;
import com.example.teamMatch.exception.UserNotFoundException;
import com.example.teamMatch.exception.ValidationException;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.RolesRepository;
import com.example.teamMatch.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.findByEmailIgnoreCase(updateUserDto.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(userIdFromToken)) {
                        throw new UserNotFoundException("Ids do not match");
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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getId().equals(userId)) {
            throw new UserNotFoundException("Ids do not match");
        };

        userRepository.delete(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        Users user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email not found " + email));

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream().map(Roles::getName).toList(),
                user.getSkills().stream().map(Skills::getTitle).toList()
        );
    }

    @Override
    public List<UserDto> findByName(String name) {
        List<Users> users = userRepository.findByNameIgnoreCase(name);

        if (users.isEmpty()) {
            throw new UserNotFoundException("User with this name not found " + name);
        }

        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream().map(Roles::getName).toList(),
                        user.getSkills().stream().map(Skills::getTitle).toList()
                ))
                .toList();
    }

    @Override
    public Users findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id not found " + id));
    }

    @Override
    public ResponseEntity<String> assignRoleToUser(UUID userId, UUID roleId) {
        Optional<Users> userOpt = userRepository.findById(userId);
        Optional<Roles> roleOpt = rolesRepository.findById(roleId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Role not found");
        }

        Users user = userOpt.get();
        user.getRoles().add(roleOpt.get());
        userRepository.save(user);

        return ResponseEntity.ok("Role assigned successfully");
    }

    @Override
    public ResponseEntity<String> removeRoleFromUser(UUID userId, UUID roleId) {
        Optional<Users> userOpt = userRepository.findById(userId);
        Optional<Roles> roleOpt = rolesRepository.findById(roleId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
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
    public String assignSkillToUser(UUID userId, UUID roleId) {
        return "";
    }

    @Override
    public String removeSkillFromUser(UUID userId, UUID roleId) {
        return "";
    }

    @Override
    public List<Skills> getUserSkills(UUID userId) {
        return List.of();
    }
}
