package com.amimul.inventoryservice.service;

import com.amimul.inventoryservice.dto.InventoryItemRequest;
import com.amimul.inventoryservice.dto.InventoryRequest;
import com.amimul.inventoryservice.dto.OrderItemRequest;
import com.amimul.inventoryservice.mapper.InventoryMapper;
import com.amimul.inventoryservice.model.Inventory;
import com.amimul.inventoryservice.model.InventoryItem;
import com.amimul.inventoryservice.repository.InventoryItemRepository;
import com.amimul.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public List<InventoryItem> getInventoryItemsMatched(List<OrderItemRequest> orderItemRequests) {
        List<InventoryItem> inventoryItemsMatched = new ArrayList<>();

        //Getting list of skuCodes
        List<String> skuCodes = orderItemRequests.stream()
                .map(orderItemRequest -> orderItemRequest.skuCode())
                .toList();
        //Getting all the inventory items matched with the given sku codes and collecting them in map
        Map<String, InventoryItem> inventoryItemsMap = inventoryItemRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .collect(Collectors.toMap(
                        InventoryItem::getSkuCode,
                        Function.identity()
                ));
        //If inventory not matched with the request throwing runtime exception
        //otherwise returning the item list
        //This is like someone checking his list and matching with products given by product delivery man
        //Found all loading them in truck and taking home
        for(OrderItemRequest orderItemRequest: orderItemRequests){
            InventoryItem inventoryItem = inventoryItemsMap.get(orderItemRequest.skuCode());
            if(inventoryItem == null || inventoryItem.getQuantity() < orderItemRequest.quantity()){
                throw new RuntimeException("Insufficient quantity available " + orderItemRequest.skuCode() );
            }
            inventoryItemsMatched.add(inventoryItem);
        }
        return inventoryItemsMatched;
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
