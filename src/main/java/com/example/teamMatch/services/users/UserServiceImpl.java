package com.example.teamMatch.services.users;

import com.example.teamMatch.dto.UpdateUserDto;
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
    public Users updateUser(UUID userId, UpdateUserDto updateUserDto) {
        return null;
    }

    @Override
    public Users deleteUser(UUID userId) {
        return null;
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
