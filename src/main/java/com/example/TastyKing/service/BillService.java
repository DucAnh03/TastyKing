package com.example.TastyKing.service;

import com.example.TastyKing.dto.request.BillRequest;
import com.example.TastyKing.dto.request.OrderRequest;
import com.example.TastyKing.dto.response.BillResponse;
import com.example.TastyKing.dto.response.OrderResponse;
import com.example.TastyKing.entity.Bill;
import com.example.TastyKing.entity.Order;
import com.example.TastyKing.exception.AppException;
import com.example.TastyKing.exception.ErrorCode;
import com.example.TastyKing.mapper.BillMapper;
import com.example.TastyKing.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillMapper billMapper;

    public BillResponse createNewBill(BillRequest request) throws IOException {
        Bill bill = billMapper.toBill(request);
        bill.setBillStatus("PROCESSING");
        Bill newBill = billRepository.save(bill);
        return billMapper.toBillResponse(newBill);
    }

    public List<BillResponse> getAllBill(){
        List<Bill> bills = billRepository.findAll();
        return bills.stream()
                .map(billMapper::toBillResponse)
                .collect(Collectors.toList());
    }

    public BillResponse getBillById(int billID){
        Bill bill = billRepository.findById(billID).orElseThrow(() ->
                new AppException(ErrorCode.BILL_NOT_EXISTED));
        return billMapper.toBillResponse(bill);
    }

    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }

    public String deleteBill(int billID){
        billRepository.deleteById(billID);
        return "Voucher deleted successfull";
    }

    public Bill getOrCreateBillWithStatusNotPayYet() {
        Optional<Bill> optionalBill = billRepository.findAll().stream()
                .filter(bill -> "not pay yet".equals(bill.getBillStatus()))
                .findFirst();
        if (optionalBill.isPresent()) {
            return optionalBill.get();
        } else {
            Bill newBill = new Bill();
            newBill.setBillStatus("not pay yet");
            newBill.setBillReleaseDate(new Date());
            return billRepository.save(newBill);
        }
    }

    public List<BillResponse> getAllBillsByStatus(String billStatus) {
        List<Bill> bills = billRepository.findByBillStatus(billStatus);
        if (bills.isEmpty()) {
            throw new AppException(ErrorCode.BILL_NOT_EXISTED);
        }
        return bills.stream()
                .map(billMapper::toBillResponse)
                .collect(Collectors.toList());
    }

    public Bill createNewBillWithStatusNotPayYet() {
        Bill newBill = new Bill();
        newBill.setBillStatus("not pay yet");
        newBill.setBillReleaseDate(new Date());
        return billRepository.save(newBill);
    }
}
