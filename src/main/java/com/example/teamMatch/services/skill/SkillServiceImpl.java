package com.example.teamMatch.services.skill;

import com.example.teamMatch.model.Skills;
import com.example.teamMatch.repositories.SkillsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillsRepository skillsRepository;

    public SkillServiceImpl(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @Override
    public ResponseEntity<Map<String, String>> addSkill(String skillTitle) {
        skillTitle = skillTitle.toUpperCase();

        if (skillsRepository.existsByTitle(skillTitle)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Skill already exists");
            return ResponseEntity.badRequest().body(response);
        }

        Skills skill = new Skills();
        skill.setTitle(skillTitle);
        skillsRepository.save(skill);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Skill added successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> removeSkill(String skillTitle) {
        skillTitle = skillTitle.toUpperCase();
        Optional<Skills> skillOpt = skillsRepository.findByTitle(skillTitle);

        if (skillOpt.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Skill not found");
            return ResponseEntity.badRequest().body(response);
        }

        skillsRepository.delete(skillOpt.get());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Skill removed successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> updateSkill(String oldTitle, String newTitle) {
        oldTitle = oldTitle.toUpperCase();
        newTitle = newTitle.toUpperCase();

        Optional<Skills> skillOpt = skillsRepository.findByTitle(oldTitle);
        if (skillOpt.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Skill not found");
            return ResponseEntity.badRequest().body(response);
        }

        Skills skill = skillOpt.get();
        skill.setTitle(newTitle);
        skillsRepository.save(skill);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Skill updated successfully");
        return ResponseEntity.ok(response);
    }
}
