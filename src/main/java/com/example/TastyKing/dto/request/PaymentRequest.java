package com.example.TastyKing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentRequest {
    private int paymentID;
    private Long orderID;
    private String customerName;
    private String paymentMethod;
    private String paymentType;
    private String paymentStatus;
    private Date paymentDate;
    private int paymentAmount;
}
