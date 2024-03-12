package com.spring_boot.mart.product.repository;

import com.spring_boot.mart.product.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByOrderByDateDesc();
    Optional<Payment> findById(Long id);
    @Query(value = "SELECT * FROM tbl_payment WHERE payment_id = :paymentId", nativeQuery = true)
    List<Payment> getRecipe(@Param("paymentId") String paymentId);
}
