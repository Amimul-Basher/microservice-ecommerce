package com.amimul.inventoryservice.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
public record InventoryItemRequest(
        @NotNull(message = "Sku Code can not be null")
        String skuCode,
        @Positive(message = "Quantity must be positive")
        int quantity,
        @NotBlank(message = "Position of item must be given")
        String position
) {
}
