package com.spring_boot.mart.product.repository;

import com.spring_boot.mart.product.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByOrderByDateDesc();

    Optional<Payment> findById(Long id);
}
