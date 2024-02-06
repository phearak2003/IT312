package com.spring_boot.mart.account.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring_boot.mart.account.entity.User;
import com.spring_boot.mart.account.repository.UserRepository;
import com.spring_boot.mart.account.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserByUsername(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<String> createUser(User user) {
        try {
            if (user != null) {
                if (userRepository.findByUsername(user.getUsername()) != null) {
                    return ResponseEntity.badRequest().body("User already exists");
                }
                user.setIsActive(1L);
                userRepository.save(user);
                return ResponseEntity.ok("User account has been created!");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create user");
        }
    }

    @Override
    public ResponseEntity<String> updateUser(User user) {
        try {
            User updateUser = userRepository.findByUsername(user.getUsername());
            if (updateUser != null) {
                updateUser.setPassword(user.getPassword());
                updateUser.setRole(user.getRole());
                updateUser.setIsActive(user.getIsActive());

                userRepository.save(updateUser);
                return ResponseEntity.ok("User account has been updated!");
            }
            return ResponseEntity.badRequest().body("Data not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update user");
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(String username) {
        try {
            User userOptional = userRepository.findByUsername(username);
            if (userOptional != null) {
                userRepository.delete(userOptional);
                return ResponseEntity.ok("User account has been deleted!");
            } else {
                return ResponseEntity.badRequest().body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete user");
        }
    }
}
