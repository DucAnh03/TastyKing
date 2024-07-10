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
public class BillRequest {
    private int billID;
    private String billStatus;
    private Date billReleaseDate;
}
