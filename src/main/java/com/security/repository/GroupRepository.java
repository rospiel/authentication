package com.security.repository;

import com.security.entity.Group;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.readOnly", value = "true")})
    Optional<Group> findByName(String name);
}
