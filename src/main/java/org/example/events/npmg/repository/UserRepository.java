package org.example.events.npmg.repository;


import org.example.events.npmg.models.Role.ERole;
import org.example.events.npmg.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Optional<Set<User>> findByRolesName(@Param("roleName") ERole role);

    @Query("select case when count(u) > 0 then true else false end " +
            "from User u join u.roles r " +
            "where u.id = :userId and r.name = :roleName")
    Boolean checkRole(@Param("userId") Long userId, @Param("roleName") ERole roleName);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}
