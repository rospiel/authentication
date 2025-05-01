package com.security.repository;

import com.security.entity.User;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.readOnly", value = "true")})
    @Query(" from User u " +
           " join fetch u.groups g " +
           " join fetch g.permissions p " +
           "where u.email = :email")
    Optional<User> findByEmail(String email);
}
