package com.example.teamMatch.services.userService;

import com.example.teamMatch.dto.UserDto;
import com.example.teamMatch.model.Roles;
import com.example.teamMatch.model.Skills;
import com.example.teamMatch.model.Timezones;
import com.example.teamMatch.model.Users;
import com.example.teamMatch.repositories.TimezoneRepository;
import com.example.teamMatch.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TimezoneRepository timezoneRepository;

    UserServiceImpl(UserRepository userRepository, TimezoneRepository timezoneRepository) {
        this.userRepository = userRepository;
        this.timezoneRepository = timezoneRepository;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Users addUser(String name, String email, String password, String timezoneName) {
        Timezones timezone = timezoneRepository.findByName(timezoneName)
                .orElseThrow(() -> new RuntimeException("Timezone not found: " + timezoneName));

        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setTimezone(timezone);

        return userRepository.save(user);
    }

    @Override
    public Users register(String name, String email, String password, String timezone) { return null; }

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
    public List<Users> findByTimezone(String timezone) {
        return List.of();
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
