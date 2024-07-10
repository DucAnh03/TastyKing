package com.example.TastyKing.repository;

import com.example.TastyKing.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByOrdersCustomerName(String customerName);
    List<Payment> findByPaymentStatus(String paymentStatus);
}
