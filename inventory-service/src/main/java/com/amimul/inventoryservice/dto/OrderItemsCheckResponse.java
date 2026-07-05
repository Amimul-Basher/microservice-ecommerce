package com.amimul.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderItemsCheckResponse {
    int allInStock;
    List<OrderItemCheckResponse> orderItemCheckResponses;
}
