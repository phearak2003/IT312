package com.spring_boot.mart.product.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring_boot.mart.product.entity.Payment;


@Service
public interface PaymentService {
    List<Payment> findAll();
    ResponseEntity<?> all();
    ResponseEntity<String> save(@RequestBody List<Payment> product);
    
}
