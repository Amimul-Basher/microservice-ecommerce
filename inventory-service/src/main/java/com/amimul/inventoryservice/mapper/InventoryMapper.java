package com.amimul.inventoryservice.mapper;

import com.amimul.inventoryservice.dto.InventoryItemRequest;
import com.amimul.inventoryservice.dto.InventoryItemResponse;
import com.amimul.inventoryservice.dto.InventoryRequest;
import com.amimul.inventoryservice.model.Inventory;
import com.amimul.inventoryservice.model.InventoryItem;

public class InventoryMapper {

    public static Inventory toInventory(InventoryRequest inventoryRequest){
        return Inventory.builder()
                .wareHouse(inventoryRequest.wareHouse())
                .date(inventoryRequest.date())
                .totalQuantity(inventoryRequest.inventoryItemRequestList()
                        .stream()
                        .map(inventoryItemRequest -> inventoryItemRequest.quantity())
                        .reduce(0, Integer::sum)
                )
                .build();
    }
    public static InventoryItem toInventoryItem(InventoryItemRequest inventoryItemRequest){
        return InventoryItem.builder()
                .skuCode(inventoryItemRequest.skuCode())
                .quantity(inventoryItemRequest.quantity())
                .position(inventoryItemRequest.position())
                .build();
    }
    public static InventoryItemResponse toInventory(InventoryItem inventoryItem){
        return InventoryItemResponse.builder()
                .skuCode(inventoryItem.getSkuCode())
                .quantity(inventoryItem.getQuantity())
                .position(inventoryItem.getPosition())
                .build();
    }
}
