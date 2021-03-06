package com.example.ordermanagement.utils.mapper;

import com.example.ordermanagement.service.bo.OrderBO;
import com.example.ordermanagement.controller.dto.CreateOrderDTO;
import com.example.ordermanagement.infrastructure.repository.entity.Order;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {UUID.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderBO toOrderBO(CreateOrderDTO createOrderDTO);

    @Mapping(target = "orderNo", expression = "java(UUID.randomUUID().toString())")
    Order toOrder(OrderBO orderBO);
}

