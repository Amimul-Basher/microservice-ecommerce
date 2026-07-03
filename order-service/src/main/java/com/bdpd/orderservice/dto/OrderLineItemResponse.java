package com.bdpd.orderservice.dto;

import com.bdpd.orderservice.model.Order;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineItemResponse(
        String skuCode,
        BigDecimal price,
        Integer quantity
){
}
