package com.example.TastyKing.mapper;

import com.example.TastyKing.dto.response.OrderDetailResponse;
import com.example.TastyKing.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {FoodMapper.class})
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(source = "food", target = "food")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}