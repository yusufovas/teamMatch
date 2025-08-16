package com.example.teamMatch.services.role;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RoleService {

    ResponseEntity<Map<String, String>> addRole(String roleTitle);
    ResponseEntity<Map<String, String>> removeRole(String roleTitle);
    ResponseEntity<Map<String, String>> updateRole(String oldTitle, String newTitle);
}
