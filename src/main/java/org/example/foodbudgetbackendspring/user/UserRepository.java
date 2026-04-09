package org.example.foodbudgetbackendspring.user;

import org.example.foodbudgetbackendspring.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
