package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Timezones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimezoneRepository extends JpaRepository<Timezones, UUID> {
    Optional<Timezones> findByName(String name);
}
