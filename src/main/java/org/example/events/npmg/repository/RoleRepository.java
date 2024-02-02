package org.example.events.npmg.repository;


import org.example.events.npmg.models.Role.ERole;
import org.example.events.npmg.models.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
