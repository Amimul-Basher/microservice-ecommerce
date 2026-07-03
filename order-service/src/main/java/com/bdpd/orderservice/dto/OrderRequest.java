package com.bdpd.orderservice.dto;

import com.bdpd.orderservice.model.OrderLineItem;
import lombok.Builder;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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
