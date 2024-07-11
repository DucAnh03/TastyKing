package com.example.TastyKing.mapper;

import com.example.TastyKing.dto.request.BillRequest;
import com.example.TastyKing.dto.response.BillResponse;
import com.example.TastyKing.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses = {OrderMapper.class})
public interface BillMapper {
    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);
    Bill toBill(BillRequest request);
    @Mapping(source = "orderList", target = "orderResponses")
    BillResponse toBillResponse(Bill bill);
}
