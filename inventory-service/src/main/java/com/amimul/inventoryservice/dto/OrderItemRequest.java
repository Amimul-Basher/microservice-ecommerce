package com.amimul.inventoryservice.dto;

import lombok.Builder;

@Builder
public record OrderItemRequest (

        //
        String skuCode,
        int quantity
){
}
