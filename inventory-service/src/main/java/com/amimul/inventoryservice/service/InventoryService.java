package com.amimul.inventoryservice.service;

import com.amimul.inventoryservice.dto.*;
import com.amimul.inventoryservice.model.InventoryItem;

import java.util.List;

public interface InventoryService {

    void doInventory(InventoryRequest inventoryRequest);

    List<InventoryItem> getInventoryItems();

    OrderItemsCheckResponse getInventoryItemsMatched(List<OrderItemCheckRequest> orderItemCheckRequests);
}
