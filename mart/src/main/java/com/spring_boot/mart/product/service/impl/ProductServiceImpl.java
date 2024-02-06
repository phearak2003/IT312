package com.spring_boot.mart.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring_boot.mart.product.entity.Product;
import com.spring_boot.mart.product.repository.ProductRepository;
import com.spring_boot.mart.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<String> updateQuantity(Long quantity, String productName) {
        try {
            Optional<Product> optionalProduct = productRepository.findByproductName(productName);
            if (optionalProduct.isPresent()) {
                Product existingProduct = optionalProduct.get();
                Long pastQuantity = existingProduct.getQuantity();
                existingProduct.setQuantity(pastQuantity - quantity);
                productRepository.save(existingProduct);
                return ResponseEntity.ok("Quantity of product '" + productName + "' has been updated to " + quantity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update quantity due to database access issue.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update quantity due to an unexpected error.");
        }
    }

    @Override
    public ResponseEntity<?> all() {
        try {
            List<Product> productList = findAll();
            return ResponseEntity.ok().body(productList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Long id, Product updatedProduct) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Product existingProduct = optionalProduct.get();
                existingProduct.setProductName(updatedProduct.getProductName());
                existingProduct.setQuantity(updatedProduct.getQuantity());
                existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
                existingProduct.setStockDate(formatter.format(date));
                existingProduct.setBarcodeNumber(updatedProduct.getBarcodeNumber());

                productRepository.save(existingProduct);

                return ResponseEntity.ok("Product has been updated successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> save(Product product) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            product.setStockDate(formatter.format(date));
            productRepository.save(product);
            return ResponseEntity.ok("Product has been added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                productRepository.delete(product);
                return ResponseEntity.ok("Product has been deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}
