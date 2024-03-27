package com.spring_boot.mart.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring_boot.mart.online.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM tbl_customer_new WHERE customer_name = :customerName and password = :password", nativeQuery = true)
    Customer findByCustomerNameAndPassword(@Param("customerName") String customerName,
            @Param("password") String password);

}
