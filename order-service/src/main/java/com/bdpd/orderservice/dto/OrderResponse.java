package com.bdpd.orderservice.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Builder
public record OrderResponse(
        String orderNumber,
        Long orderPlacedBy,
        List<OrderLineItemResponse> orderLineItemResponseList
) {
}
