package com.amimul.inventoryservice.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderItemCheckResponse{
    String skuCode;
    int quantity;
    String status;
}
