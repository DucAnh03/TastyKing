package com.example.TastyKing.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billID;

    @OneToMany(mappedBy = "bill",fetch = FetchType.LAZY)
    private List<Order> orderList;

    @Column(name = "BillStatus",nullable = false)
    private String billStatus;

    @Column(name = "BillReleaseDate",nullable = false)
    private Date billReleaseDate;
}
