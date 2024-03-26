package com.spring_boot.mart.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_boot.mart.online.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
