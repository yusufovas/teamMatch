package com.example.teamMatch.services.userService;

import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Users addUser(UserDto userDto) {
       if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public Users register(UserDto userDto) {
        Users user = new Users();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public boolean updatePassword(UUID userId, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public Users updateUser(UUID userId, String oldName, String newName, String oldEmail, String newEmail) {
        return null;
    }

    @Override
    public Users deleteUser(UUID userId) {
        return null;
    }

    @Override
    public List<Users> searchUsersBySkill(String skillName) {
        return List.of();
    }

    @Override
    public List<Roles> searchUsersByRole(String roleName) {
        return List.of();
    }

    @Override
    public Users findByEmail(String email) {
        return null;
    }

    @Override
    public Users findByName(String name) {
        return null;
    }

    @Override
    public Users findById(UUID id) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean assignRoleToUser(UUID userId, UUID roleId) {
        return false;
    }

    @Override
    public boolean removeRoleFromUser(UUID userId, UUID roleId) {
        return false;
    }

    @Override
    public List<Roles> getUserRoles(UUID userId) {
        return List.of();
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
