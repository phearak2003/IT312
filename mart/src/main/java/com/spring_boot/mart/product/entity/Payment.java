package com.spring_boot.mart.product.entity;

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
@Table(name = "tbl_payment")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "product_name")
    String productName;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "unit_price")
    double unitPrice;

    @Column(name = "date")
    String date;

    @Column(name = "cashier")
    String cashier;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "payment_id")
    String paymentId;
}