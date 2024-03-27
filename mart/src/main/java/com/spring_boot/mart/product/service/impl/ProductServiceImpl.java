package com.spring_boot.mart.product.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring_boot.mart.product.entity.Product;
import com.spring_boot.mart.product.repository.ProductRepository;
import com.spring_boot.mart.product.service.ProductService;
import java.nio.file.Path;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${upload.dir_foronline}")
    private String uploadDir_online;

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
    public ResponseEntity<String> update(Long id, Product updatedProduct, MultipartFile productImage) {
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

                // Save product image if provided
                if (productImage != null && !productImage.isEmpty()) {
                    String fileName = existingProduct.getId() + "_" + existingProduct.getProductName().replace(" ", "_")
                            + "_"
                            + productImage.getOriginalFilename();

                    // Constructing the upload directory relative to the project directory
                    Path projectDirectory = Paths.get("").toAbsolutePath();

                    Path uploadPath1 = Paths.get(projectDirectory.toString(), uploadDir);
                    Path uploadPath2 = Paths.get(projectDirectory.toString(), uploadDir_online);
                    Path filePath1 = Paths.get(uploadPath1.toString(), fileName);
                    Path filePath2 = Paths.get(uploadPath2.toString(), fileName);

                    // Ensure the directories exist, create them if necessary
                    Files.createDirectories(uploadPath1);
                    Files.createDirectories(uploadPath2);

                    // Write the file to the constructed path
                    byte[] bytes = productImage.getBytes();
                    Files.write(filePath1, bytes);
                    Files.write(filePath2, bytes);

                    existingProduct.setImagePath(fileName);
                }

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
    public ResponseEntity<String> save(Product product, MultipartFile productImage) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            // Set stock date
            product.setStockDate(formatter.format(date));

            // Save product
            Product savedProduct = productRepository.save(product);

            // Save product image if provided
            if (productImage != null && !productImage.isEmpty()) {
                String fileName = savedProduct.getId() + "_" + savedProduct.getProductName().replace(" ", "_") + "_"
                        + productImage.getOriginalFilename();
                byte[] bytes = productImage.getBytes();

                // Constructing the upload directory relative to the project directory
                Path projectDirectory = Paths.get("").toAbsolutePath();

                Path uploadPath1 = Paths.get(projectDirectory.toString(), uploadDir);
                Path uploadPath2 = Paths.get(projectDirectory.toString(), uploadDir_online);
                Path filePath1 = Paths.get(uploadPath1.toString(), fileName);
                Path filePath2 = Paths.get(uploadPath2.toString(), fileName);

                // Ensure the directories exist, create them if necessary
                Files.createDirectories(uploadPath1);
                Files.createDirectories(uploadPath2);

                Files.write(filePath1, bytes);
                Files.write(filePath2, bytes);

                savedProduct.setImagePath(fileName);
                productRepository.save(savedProduct);
            }
            return ResponseEntity.ok("Product has been added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving image.");
        } catch (Exception e) {
            e.printStackTrace();
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
