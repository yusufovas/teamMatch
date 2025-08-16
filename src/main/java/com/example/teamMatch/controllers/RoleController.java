package com.example.teamMatch.controllers;

import com.example.teamMatch.services.role.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addRole(@RequestBody Map<String, String> request) {
        String roleTitle = request.get("roleTitle");
        return roleService.addRole(roleTitle);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeRole(@RequestBody Map<String, String> request) {
        String roleTitle = request.get("roleTitle");
        return roleService.removeRole(roleTitle);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateRole(@RequestBody Map<String, String> request) {
        String oldTitle = request.get("oldTitle");
        String newTitle = request.get("newTitle");
        return roleService.updateRole(oldTitle, newTitle);
    }
}
