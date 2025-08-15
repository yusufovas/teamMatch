package com.example.teamMatch.repositories;

import com.example.teamMatch.dto.user.UserDto;
import com.example.teamMatch.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmailIgnoreCase(String email);

    @Query("SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.skills WHERE LOWER(u.name) = LOWER(:name)")
    List<Users> findByNameIgnoreCase(@Param("name") String name);

    @Query("SELECT u FROM Users u JOIN FETCH u.roles WHERE u.id = :userId")
    Optional<Users> findByIdWithRoles(@Param("userId") UUID userId);

    boolean existsByEmail(String email);

}
