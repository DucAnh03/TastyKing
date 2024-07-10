package com.example.TastyKing.controller;

import com.example.TastyKing.dto.request.BillRequest;
import com.example.TastyKing.dto.response.ApiResponse;
import com.example.TastyKing.dto.response.BillResponse;
import com.example.TastyKing.entity.Bill;
import com.example.TastyKing.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping
    public ApiResponse<BillResponse> createNewBill(@RequestBody @Valid BillRequest request) throws IOException{
        return ApiResponse.<BillResponse>builder()
                .result(billService.createNewBill(request))
                .build();
    }

    @GetMapping("/get")
    public ApiResponse<List<BillResponse>> getAllBill() {
        return ApiResponse.<List<BillResponse>>builder()
                .result(billService.getAllBill())
                .build();
    }

    @GetMapping("/get/{billID}")
    public ApiResponse<BillResponse> getBillByID(@PathVariable("billID") Integer billID){
        return ApiResponse.<BillResponse>builder()
                .result(billService.getBillById(billID))
                .build();
    }

    @GetMapping("/get/status/{billStatus}")
    public ApiResponse<List<BillResponse>> getAllBillsByStatus(@PathVariable("billStatus") String billStatus) {
        return ApiResponse.<List<BillResponse>>builder()
                .result(billService.getAllBillsByStatus(billStatus))
                .build();
    }

    @DeleteMapping("/delete/{billID}")
    public ApiResponse<String> deleteBill(@PathVariable("billID") Integer billID){
        return ApiResponse.<String>builder()
                .result(billService.deleteBill(billID))
                .build();
    }

}
