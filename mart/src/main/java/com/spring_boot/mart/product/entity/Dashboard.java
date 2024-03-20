package com.spring_boot.mart.product.entity;

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
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long paymentThisMonth;
    Long paymentToday;
    Long productCount;
    Float total;
}
