package com.example.TastyKing.mapper;

import com.example.TastyKing.dto.request.OrderDetailRequest;
import com.example.TastyKing.dto.request.OrderRequest;
import com.example.TastyKing.dto.request.OrderUpdateRequest;
import com.example.TastyKing.dto.response.OrderResponse;
import com.example.TastyKing.entity.Food;
import com.example.TastyKing.entity.Order;
import com.example.TastyKing.entity.OrderDetail;
import com.example.TastyKing.entity.OrderDetailId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "billID", target = "bill.billID")
    Order toOrder(OrderRequest request);

    @Mapping(source = "bill.billID", target = "billID")
    OrderResponse toOrderResponse(Order order);

    void updateOrderFromRequest(OrderRequest request, @MappingTarget Order order);

}
