package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
    Optional<Roles> findByName(String name);
}
