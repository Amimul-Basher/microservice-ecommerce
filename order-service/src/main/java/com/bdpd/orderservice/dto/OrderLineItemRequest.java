package com.bdpd.orderservice.dto;

import com.bdpd.orderservice.model.Order;
import lombok.Builder;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
