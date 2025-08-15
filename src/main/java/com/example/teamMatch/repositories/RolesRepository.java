package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
    List<Roles> findByNameIn(List<String> upperCaseTitles);
}
