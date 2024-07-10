package com.example.TastyKing.dto.response;

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
public class BillResponse {
    private int billID;
    private String billStatus;
    private Date billReleaseDate;
    private List<OrderResponse> orderResponses;

}
