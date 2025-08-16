package com.example.teamMatch.services.skill;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface SkillService {
    ResponseEntity<Map<String, String>> addSkill(String skillTitle);
    ResponseEntity<Map<String, String>> removeSkill(String skillTitle);
    ResponseEntity<Map<String, String>> updateSkill(String oldTitle, String newTitle);
}
