package com.spring_boot.mart.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring_boot.mart.product.dto.ListProductDto;
import com.spring_boot.mart.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @SuppressWarnings("null")
    Optional<Product> findById(Long id);

    Optional<Product> findByproductName(String product_name);

    @Query(value = "SELECT product_name FROM tbl_product ", nativeQuery = true)
    List<Object[]> getAll();

    @Query(value = "SELECT COUNT(*) FROM tbl_product ", nativeQuery = true)
    Long productCount();

    @Query(value = "SELECT unit_price FROM tbl_product WHERE product_name = :product ", nativeQuery = true)
    List<Object[]> getPrice(@Param("product") String product);

    @Query(value = "SELECT quantity FROM tbl_product WHERE product_name = :product  ", nativeQuery = true)
    List<Object[]> getQuantity(@Param("product") String product);
    
    @Query(value = "SELECT product_name,unit_price FROM tbl_product WHERE barcode = :barcode  ", nativeQuery = true)
    List<Object[]> getProductWithBarCode(@Param("barcode") String barcode);

    @Query(nativeQuery = true, value = "SELECT id AS Id, image_path AS Image, product_name AS Name, unit_price AS Up FROM tbl_product ")
    List<ListProductDto> listProducts();
}
