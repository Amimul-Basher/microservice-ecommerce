package com.amimul.inventoryservice.dto;

import com.amimul.inventoryservice.model.InventoryItem;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Builder
public record InventoryResponse(
        String inventoryNumber,
        String wareHouse,
        Double totalPrice,
        Date date,
        List<InventoryItem> inventoryItemList
) {
}
