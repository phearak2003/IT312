package com.spring_boot.mart.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring_boot.mart.online.entity.Cart;
import com.spring_boot.mart.product.entity.Payment;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM tbl_cart WHERE cart_id = :cartid", nativeQuery = true)
    List<Cart> getCartbycartID(@Param("cartid") String cartid);
}
