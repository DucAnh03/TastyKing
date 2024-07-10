package com.example.TastyKing.controller;

import com.example.TastyKing.dto.request.PaymentRequest;
import com.example.TastyKing.dto.response.ApiResponse;
import com.example.TastyKing.dto.response.PaymentResponse;
import com.example.TastyKing.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ApiResponse<PaymentResponse> createNewPayment(@RequestBody @Valid PaymentRequest request) throws IOException{
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createNewPayment(request))
                .build();
    }

    @GetMapping("/get")
    public ApiResponse<List<PaymentResponse>> getAllPayment(){
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPayment())
                .build();
    }

    @GetMapping("/get/{paymentID}")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable("paymentID") Integer paymentID){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.getPaymentByID(paymentID))
                .build();
    }

    @GetMapping("/get/customer/{customerName}")
    public ApiResponse<List<PaymentResponse>> getPaymentsByCustomerName(@PathVariable("customerName") String customerName) {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getPaymentsByCustomerName(customerName))
                .build();
    }

    @GetMapping("/get/status/{paymentStatus}")
    public ApiResponse<List<PaymentResponse>> getAllPaymentsByStatus(@PathVariable("paymentStatus") String paymentStatus) {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPaymentsByStatus(paymentStatus))
                .build();
    }

    @PatchMapping("/update/{paymentID}/status")
    public ApiResponse<PaymentResponse> updatePaymentStatus(@PathVariable("paymentID") int paymentID, @RequestBody Map<String, String> request) {
        String newStatus = request.get("paymentStatus");
        paymentService.updatePaymentStatus(paymentID, newStatus);
        PaymentResponse updatedPayment = paymentService.getPaymentByID(paymentID);
        return ApiResponse.<PaymentResponse>builder()
                .result(updatedPayment)
                .build();
    }

    @GetMapping("/vnpay_payment")
    public String handlePaymentReturn(HttpServletRequest request, Model model){
        int paymentStatus = paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }

}
