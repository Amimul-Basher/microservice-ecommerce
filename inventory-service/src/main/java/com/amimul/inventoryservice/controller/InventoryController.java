package com.amimul.inventoryservice.controller;

import com.amimul.inventoryservice.dto.InventoryItemRequest;
import com.amimul.inventoryservice.dto.InventoryRequest;
import com.amimul.inventoryservice.dto.OrderItemRequest;
import com.amimul.inventoryservice.model.InventoryItem;
import com.amimul.inventoryservice.repository.InventoryItemRepository;
import com.amimul.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {


//    private final InventoryService inventoryService;
//    public InventoryController(
//            @Qualifier("inventoryServiceImpl1")
//            InventoryService inventoryService) {
//        this.inventoryService = inventoryService;
//    }

    @Qualifier("inventoryServiceImplementation")
    private final InventoryService inventoryService;
    private final InventoryItemRepository inventoryItemRepository;

    @PostMapping
    public ResponseEntity<String> doInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.doInventory(inventoryRequest);

        return ResponseEntity.ok("inventory done");
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems(){
        return ResponseEntity.ok(inventoryService.getInventoryItems());
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getInventoryItemsMatched(
            @RequestParam List<OrderItemRequest> orderItemRequests
    ){
        return ResponseEntity.ok(inventoryService.getInventoryItemsMatched(orderItemRequests));
    }

}
