package com.bdpd.orderservice.dto;

import com.bdpd.orderservice.model.OrderLineItem;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Builder
public record OrderRequest (
        Long id,
        @NotNull
        String orderNumber,
        Long orderPlacedBy,
        @NotNull(message = "LineItems can't be null")
        List<OrderLineItemRequest> orderLineItemRequestList
){
}
