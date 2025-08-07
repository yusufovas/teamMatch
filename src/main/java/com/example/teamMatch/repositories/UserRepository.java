package com.example.teamMatch.repositories;

import com.example.teamMatch.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmailIgnoreCase(String email);
    Optional<Users> findByNameIgnoreCase(String name);

    boolean existsByEmail(String email);
}
