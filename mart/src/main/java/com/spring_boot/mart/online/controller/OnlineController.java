package com.spring_boot.mart.online.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.mart.product.dto.ListProductDto;
import com.spring_boot.mart.product.repository.ProductRepository;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/online")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OnlineController {
    private final ProductRepository productRepository;

    @GetMapping("/list-product")
    public List<ListProductDto> products() {
        return productRepository.listProducts();
    }
}
