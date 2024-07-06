package com.example.TastyKing.service;

import com.example.TastyKing.dto.request.VoucherExchangeRequest;
import com.example.TastyKing.dto.response.VoucherExchangeResponse;
import com.example.TastyKing.dto.response.VoucherResponse;
import com.example.TastyKing.entity.RewardPoint;
import com.example.TastyKing.entity.User;
import com.example.TastyKing.entity.Voucher;
import com.example.TastyKing.entity.VoucherExchange;
import com.example.TastyKing.exception.AppException;
import com.example.TastyKing.exception.ErrorCode;
import com.example.TastyKing.repository.RewardPointRepository;
import com.example.TastyKing.repository.UserRepository;
import com.example.TastyKing.repository.VoucherExchangeRepository;
import com.example.TastyKing.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherExchangeService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoucherExchangeRepository voucherExchangeRepository;
    @Autowired
    private RewardPointRepository rewardPointRepository;



    public VoucherExchangeResponse createVoucherExchange(VoucherExchangeRequest request) {
        User user = userRepository.findById(request.getUser().getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        Voucher voucher = voucherRepository.findByVoucherId(request.getVoucher().getVoucherId())
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_EXIST));
        LocalDateTime exchangeDate = LocalDateTime.now();

        // Check if user has enough reward points
        RewardPoint rewardPoint = rewardPointRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.POINT_EMPTY));

        if (rewardPoint.getBalance() < voucher.getVoucherExchangePoint()) {
            throw new AppException(ErrorCode.POINT_NOT_ENOUGH);
        }

        // Check if voucher has enough quantity
        if (voucher.getVoucherQuantity() <= voucher.getNumberVoucherUsed()) {
            throw new AppException(ErrorCode.VOUCHER_NOT_ENOUGH);
        }

        // Deduct reward points from user
        rewardPoint.setBalance(rewardPoint.getBalance() - voucher.getVoucherExchangePoint());
        rewardPointRepository.save(rewardPoint);

        // Increment number of voucher used
        voucher.setNumberVoucherUsed(voucher.getNumberVoucherUsed() + 1);
        voucherRepository.save(voucher);

        // Create and save VoucherExchange entity
        VoucherExchange voucherExchange = VoucherExchange.builder()
                .user(user)
                .voucher(voucher)
                .exchangeDate(exchangeDate)
                .build();
        voucherExchange = voucherExchangeRepository.save(voucherExchange);

        // Create response
        return VoucherExchangeResponse.builder()
                .voucherExchangeId(voucherExchange.getVoucherExchangeId())
                .user(voucherExchange.getUser())
                .voucher(voucherExchange.getVoucher())
                .voucherExchangeDate(voucherExchange.getExchangeDate())
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
        public List<VoucherExchangeResponse> getAllVoucherExchange(){
            List<VoucherExchange> voucherExchanges = voucherExchangeRepository.findAll();
            return voucherExchanges.stream()
                    .map(voucherExchange -> VoucherExchangeResponse.builder()
                            .voucherExchangeId(voucherExchange.getVoucherExchangeId())
                            .user(voucherExchange.getUser())
                            .voucher(voucherExchange.getVoucher())
                            .voucherExchangeDate(voucherExchange.getExchangeDate())
                            .build()).collect(Collectors.toList());
        }

        public VoucherExchangeResponse getVoucherExchangeByID(Long voucherExchangeId){
            VoucherExchange voucherExchange = voucherExchangeRepository.findById(voucherExchangeId).orElseThrow(() -> new AppException(ErrorCode.EXCHANGE_NOT_EXIST));
            return VoucherExchangeResponse.builder()
                    .voucherExchangeId(voucherExchange.getVoucherExchangeId())
                    .user(voucherExchange.getUser())
                    .voucher(voucherExchange.getVoucher())
                    .voucherExchangeDate(voucherExchange.getExchangeDate())
                    .build();
        }

    @PostAuthorize("returnObject.email == principal.claims['sub']")
    public List<VoucherExchangeResponse> getVoucherExchangeByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        List<VoucherExchange> voucherExchanges = voucherExchangeRepository.findByUser(user);
        return voucherExchanges.stream()
                .map(voucherExchange -> VoucherExchangeResponse.builder()
                        .voucherExchangeId(voucherExchange.getVoucherExchangeId())
                        .user(voucherExchange.getUser())
                        .voucher(voucherExchange.getVoucher())
                        .voucherExchangeDate(voucherExchange.getExchangeDate())
                        .build()).collect(Collectors.toList());
    }

}