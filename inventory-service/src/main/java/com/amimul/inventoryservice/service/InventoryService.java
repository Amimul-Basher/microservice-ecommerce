package com.amimul.inventoryservice.service;

import com.amimul.inventoryservice.dto.InventoryItemRequest;
import com.amimul.inventoryservice.dto.InventoryRequest;
import com.amimul.inventoryservice.dto.OrderItemRequest;
import com.amimul.inventoryservice.model.InventoryItem;

import java.util.List;

public interface InventoryService {

    void doInventory(InventoryRequest inventoryRequest);

    List<InventoryItem> getInventoryItems();

    List<InventoryItem> getInventoryItemsMatched(List<OrderItemRequest> orderItemRequests);
}
