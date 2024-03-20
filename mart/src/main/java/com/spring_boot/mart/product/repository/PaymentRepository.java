package com.spring_boot.mart.product.repository;

import com.spring_boot.mart.product.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByOrderByDateDesc();

    Optional<Payment> findById(Long id);

    @Query(value = "SELECT * FROM tbl_payment WHERE payment_id = :paymentId", nativeQuery = true)
    List<Payment> getRecipe(@Param("paymentId") String paymentId);

    @Query(value = "SELECT COUNT(*) AS payment_count FROM tbl_payment WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = MONTH(CURDATE()) AND YEAR(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = YEAR(CURDATE())", nativeQuery = true)
    Long paymentThisMonth();

    @Query(value = "SELECT MONTH(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) AS payment_month, COUNT(*) AS payment_count,SUM(quantity * unit_price) AS total_sale_money FROM tbl_payment WHERE YEAR(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = YEAR(CURDATE()) GROUP BY payment_month;", nativeQuery = true)
    List<Object[]> paymentEveryMonth();

    @Query(value = "SELECT product_name, SUM(quantity) AS total_quantity\r\n" + //
            "FROM tbl_payment\r\n" + //
            "WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = MONTH(CURDATE())\r\n" + //
            "AND YEAR(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = YEAR(CURDATE())\r\n" + //
            "GROUP BY product_name \r\n" + //
            "ORDER BY `total_quantity` DESC LIMIT 3", nativeQuery = true)
    List<Object[]> bestSaleThisMonth();

    @Query(value = "SELECT SUM(quantity * unit_price) AS total_sale_money FROM tbl_payment WHERE MONTH(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = MONTH(CURDATE()) AND YEAR(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = YEAR(CURDATE());", nativeQuery = true)
    Float totalThisMonth();

    @Query(value = "SELECT COUNT(*) AS payment_count FROM tbl_payment WHERE DATE(STR_TO_DATE(date, '%d/%m/%Y %H:%i:%s')) = CURDATE()", nativeQuery = true)
    Long paymentToday();
}
