package com.amimul.inventoryservice.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Builder
public record InventoryRequest(
        String wareHouse,
        Double totalQuantity,
        Date date,
        @NotNull(message = "Inventory can not be created without items")
        List<InventoryItemRequest>inventoryItemRequestList
) {
}
