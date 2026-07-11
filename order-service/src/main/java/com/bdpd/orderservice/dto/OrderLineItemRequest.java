package com.bdpd.orderservice.dto;

import com.bdpd.orderservice.model.Order;
import lombok.Builder;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
public record OrderLineItemRequest(
        Long id,
        @NotNull(message = "Sku Code can not be null")
        String skuCode,
        @DecimalMin(value="0.0", inclusive = false)
        BigDecimal price,
        @Positive(message = "Quantity can not be negative")
        Integer quantity
//        Order order
) {
}
