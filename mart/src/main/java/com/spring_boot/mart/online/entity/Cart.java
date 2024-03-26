package com.spring_boot.mart.online.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "cart_id")
    String cartId;

    @Column(name = "product_id")
    String productId;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "date")
    String date;

    @Column(name = "customer_id")
    Long customerId;

    @Column(name = "is_checkout")
    Long isCheckout;

    @Column(name = "latitute")
    String latitute;

    @Column(name = "longtitute")
    String longtitute;

    @Column(name = "is_delivery")
    Long isDelivery;
}