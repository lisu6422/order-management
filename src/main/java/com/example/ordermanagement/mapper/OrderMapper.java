package com.example.ordermanagement.mapper;

import com.example.ordermanagement.bo.OrderBO;
import com.example.ordermanagement.dto.CreateOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderBO toOrderBO(CreateOrderDTO createOrderDTO);
}

