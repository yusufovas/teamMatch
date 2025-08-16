package com.example.teamMatch.controllers;

import com.example.teamMatch.services.skill.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addSkill(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        return skillService.addSkill(title);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeSkill(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        return skillService.removeSkill(title);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateSkill(@RequestBody Map<String, String> body) {
        String oldTitle = body.get("oldTitle");
        String newTitle = body.get("newTitle");
        return skillService.updateSkill(oldTitle, newTitle);
    }
}
