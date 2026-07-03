package com.amimul.inventoryservice.dto;

import lombok.Builder;

@Builder
public record InventoryItemResponse(
        String skuCode,
        int quantity,
        String position
) {
}
