package com.bdpd.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderItemCheckRequest(

        //
        String skuCode,
        int quantity
){
}
