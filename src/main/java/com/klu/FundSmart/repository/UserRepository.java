package com.klu.FundSmart.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klu.FundSmart.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check user login by username, email, and password
    @Query("SELECT u FROM User u WHERE (u.username = ?1 OR u.email = ?2) AND u.password = ?3")
    public User checkUserLogin(String username, String email, String password);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by phone number
    Optional<User> findByPhno(String phno);

    // Find user by username
    Optional<User> findByUsername(String username);
}