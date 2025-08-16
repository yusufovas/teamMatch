package com.example.teamMatch.services.role;

import com.example.teamMatch.model.Roles;
import com.example.teamMatch.repositories.RolesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public ResponseEntity<Map<String, String>> addRole(String roleTitle) {
        Map<String, String> response = new HashMap<>();
        String upperTitle = roleTitle.toUpperCase();

        if (rolesRepository.existsByName(upperTitle)) {
            response.put("error", "Role already exists");
            return ResponseEntity.badRequest().body(response);
        }

        Roles newRole = new Roles();
        newRole.setName(upperTitle);
        rolesRepository.save(newRole);

        response.put("message", "Role added successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> removeRole(String roleTitle) {
        Map<String, String> response = new HashMap<>();
        String upperTitle = roleTitle.toUpperCase();

        Optional<Roles> roleOpt = rolesRepository.findByName(upperTitle);
        if (roleOpt.isEmpty()) {
            response.put("error", "Role not found");
            return ResponseEntity.badRequest().body(response);
        }

        rolesRepository.delete(roleOpt.get());
        response.put("message", "Role removed successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> updateRole(String oldTitle, String newTitle) {
        Map<String, String> response = new HashMap<>();

        String oldUpper = oldTitle.toUpperCase();
        String newUpper = newTitle.toUpperCase();

        Optional<Roles> roleOpt = rolesRepository.findByName(oldUpper);
        if (roleOpt.isEmpty()) {
            response.put("error", "Role not found: " + oldTitle);
            return ResponseEntity.badRequest().body(response);
        }

        if (rolesRepository.existsByName(newUpper)) {
            response.put("error", "Role with new title already exists: " + newTitle);
            return ResponseEntity.badRequest().body(response);
        }

        Roles role = roleOpt.get();
        role.setName(newUpper);
        rolesRepository.save(role);

        response.put("message", "Role updated successfully from '" + oldTitle + "' to '" + newTitle + "'");
        return ResponseEntity.ok(response);
    }

}
