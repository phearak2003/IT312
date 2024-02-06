package com.spring_boot.mart.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring_boot.mart.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    Optional<Product> findByproductName(String product_name);

    @Query(value = "SELECT product_name FROM tbl_product ", nativeQuery = true)
    List<Object[]> getAll();

    @Query(value = "SELECT unit_price FROM tbl_product WHERE product_name = :product ", nativeQuery = true)
    List<Object[]> getPrice(@Param("product") String product);

    @Query(value = "SELECT quantity FROM tbl_product WHERE product_name = :product  ", nativeQuery = true)
    List<Object[]> getQuantity(@Param("product") String product);

}
