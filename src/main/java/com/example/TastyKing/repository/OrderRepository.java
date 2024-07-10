package com.example.TastyKing.repository;

import com.example.TastyKing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_email(String email);

    List<Order> findByCustomerName(String customerName);
    List<Order> findByCustomerNameAndOrderStatus(String customerName, String orderStatus);
    List<Order> findByOrderStatus(String orderStatus);
}
