package com.example.TastyKing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "voucherexchange")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VoucherExchangeID")
    private Long voucherExchangeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VoucherID", nullable = false)
    private Voucher voucher;

    @Column(name = "ExchangeDate", nullable = false)
    private LocalDateTime exchangeDate;
}