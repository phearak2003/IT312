package com.spring_boot.mart.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_boot.mart.account.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsernameAndPassword(String username, String password);
   User findByUsername(String username);
   Optional<?> deleteByUsername(String username);
}
