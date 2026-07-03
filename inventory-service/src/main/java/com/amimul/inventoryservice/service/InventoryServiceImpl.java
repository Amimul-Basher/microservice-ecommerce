package com.amimul.inventoryservice.service;

import com.amimul.inventoryservice.dto.InventoryItemRequest;
import com.amimul.inventoryservice.dto.InventoryRequest;
import com.amimul.inventoryservice.mapper.InventoryMapper;
import com.amimul.inventoryservice.model.Inventory;
import com.amimul.inventoryservice.model.InventoryItem;
import com.amimul.inventoryservice.repository.InventoryItemRepository;
import com.amimul.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("inventoryServiceImplementation")
@Primary
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryItemRepository inventoryItemRepository;

    private final InventoryRepository inventoryRepository;
    @Override
    public void doInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = InventoryMapper.toInventory(inventoryRequest);
        inventory.setInventoryNumber(UUID.randomUUID().toString());
        List<InventoryItem> inventoryItems = inventoryRequest.inventoryItemRequestList()
                .stream()
                .map(this::processInventoryItemRequest)
                .toList();
        inventory.setInventoryItemList(inventoryItems);
        //This is used to maintain 2 way relation means feeling the order id column
        for(var inventoryItem : inventoryItems){
            inventoryItem.setInventory(inventory);
        }
        // Save the inventory to the map
//        inventoryRepository.save(inventoryItem);
        inventoryRepository.save(inventory);
    }
    @Override
    public List<InventoryItem> getInventoryItems() {
        return inventoryItemRepository.findAll();
    }
    private InventoryItem processInventoryItemRequest(InventoryItemRequest inventoryItemRequest){
        //Find inventory item with the sku code coming form the request
        Optional<InventoryItem> inventoryOptional = inventoryItemRepository.findBySkuCode(inventoryItemRequest.skuCode());
//        Inventory inventory = inventoryOptional
//                .orElseThrow(()->new RuntimeException("Inventory Not found with SkuCode" + inventoryRequest.skuCode()));
        InventoryItem inventoryItem = null;
        //If inventory item is already found update the inventory with extra items.
        //else map inventory item request to inventory
        if(inventoryOptional.isPresent()){
            inventoryItem = inventoryOptional.get();;
            inventoryItem.setQuantity(inventoryItem.getQuantity()  + inventoryItemRequest.quantity());
            if(!inventoryItem.getPosition().equals(inventoryItemRequest.position())){
                inventoryItem.setPosition( inventoryItem.getPosition() + " " + inventoryItemRequest.position());
            }
        }else{
            inventoryItem = InventoryMapper.toInventoryItem(inventoryItemRequest);
        }
        return inventoryItem;
    }
}
