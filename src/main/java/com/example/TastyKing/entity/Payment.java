package com.example.TastyKing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID",nullable = true)
    private User user;

    @OneToMany(mappedBy = "payment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "PaymentMethod",nullable = false)
    private String paymentMethod;

    @Column(name = "PaymentType")
    private String paymentType;

    @Column(name = "PaymentStatus")
    private String paymentStatus;

    @Column(name = "PaymentDate")
    private Date paymentDate;

    @Column(name = "PaymentAmount",nullable = false)
    private int paymentAmount;
}
