package com.example.teamMatch.controllers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {
    private final JdbcTemplate template;

    public TeamController(JdbcTemplate template) {
        this.template = template;
    }

    @GetMapping("/")
    public String checkDbConnection() {

        try {
            String name = template.queryForObject(
                    "SELECT name FROM users LIMIT 1",
                    String.class
            );
            return "Connected to DB, first user: " + name;
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}
