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
@Table(name = "tbl_product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "product_name")
    String productName;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "unit_price")
    String unitPrice;

    @Column(name = "barcode")
    String barcodeNumber;

    @Column(name = "inputter")
    String inputter;

    @Column(name = "stock_date")
    String stockDate;

    @Column(name = "image_path") 
    String imagePath;
}