package com.spring_boot.mart.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring_boot.mart.product.entity.Payment;
import com.spring_boot.mart.product.repository.PaymentRepository;
import com.spring_boot.mart.product.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository productRepository;

    @Override
    public List<Payment> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<?> all() {
        try {
            List<Payment> productList = findAll();
            return ResponseEntity.ok().body(productList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> save(List<Payment> product) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            UUID uuid = UUID.randomUUID();
            String paymentId = uuid.toString().substring(0, 8);
            List<Payment> payments = new ArrayList<>();

            for (Payment data : product) {
                Payment payment = new Payment();
                payment.setProductName(data.getProductName());
                payment.setQuantity(data.getQuantity());
                payment.setUnitPrice(data.getUnitPrice());
                payment.setDate(formatter.format(date).toString());
                payment.setCashier(data.getCashier());
                payment.setCustomerName(data.getCustomerName());
                payment.setPaymentId(paymentId);

                payments.add(payment);
            }

            productRepository.saveAll(payments);
            return ResponseEntity.ok("Product has been added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
