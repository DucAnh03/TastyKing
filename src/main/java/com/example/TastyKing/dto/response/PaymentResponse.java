package com.example.TastyKing.dto.response;

import com.example.TastyKing.dto.response.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentResponse {
    private int paymentID;
    private String paymentStatus;
    private String paymentMethod;
    private String paymentType;
    private Date paymentDate;
    private int paymentAmount;
    private List<OrderResponse> orders;
    private String paymentUrl; // Thêm trường này để chứa URL thanh toán
}
