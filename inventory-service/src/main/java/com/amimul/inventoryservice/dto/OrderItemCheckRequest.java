package com.amimul.inventoryservice.dto;

import lombok.Builder;

@Builder
public record OrderItemCheckRequest(

        //
        String skuCode,
        int quantity
){
}
