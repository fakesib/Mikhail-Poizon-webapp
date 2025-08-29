package com.fakesibwork.database.repository;

import com.fakesibwork.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "update users set verify_token = null where verify_token = ?1", nativeQuery = true)
    void updateVerifyToken(String verify_token);

    Optional<User> findByEmail(String email);
}
