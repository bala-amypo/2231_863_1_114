package com.yourpackage.repository;

import com.yourpackage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find user for login
    Optional<User> findByUsername(String username);
    
    // Check if username/email already exists for registration
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}