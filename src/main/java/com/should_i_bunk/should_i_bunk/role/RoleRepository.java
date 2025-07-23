package com.should_i_bunk.should_i_bunk.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(String roleUser);
    // This interface will automatically provide CRUD operations for Role entities
    // and can be extended with custom query methods if needed.
}
