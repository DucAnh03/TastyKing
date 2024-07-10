package com.example.TastyKing.repository;

import com.example.TastyKing.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    boolean existsByBillStatus(String status);
    List<Bill> findByBillStatus(String billStatus);
}
