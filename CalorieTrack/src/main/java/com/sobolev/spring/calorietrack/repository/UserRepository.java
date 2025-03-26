package com.sobolev.spring.calorietrack.repository;

import com.sobolev.spring.calorietrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<User> findById(Long id);
}
