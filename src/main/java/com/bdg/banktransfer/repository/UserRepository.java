package com.bdg.banktransfer.repository;

import com.bdg.banktransfer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User signIn(String email, String password);
    User getByEmailAndPassword(String email, String password);
}