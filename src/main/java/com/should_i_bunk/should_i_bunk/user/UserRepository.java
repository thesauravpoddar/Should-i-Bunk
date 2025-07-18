package com.should_i_bunk.should_i_bunk.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {
    Optional<User> findByEmail(String email);
}
