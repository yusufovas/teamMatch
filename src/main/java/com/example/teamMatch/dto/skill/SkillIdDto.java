package com.example.teamMatch.dto.skill;

import java.util.UUID;

public class SkillIdDto {

    private UUID skillId;
    private String title;

    public SkillIdDto(UUID id, String title) {
        this.skillId = id;
        this.title = title;
    }

    public UUID getSkillId() {
        return skillId;
    }

    public String getTitle() { return title; }
}
