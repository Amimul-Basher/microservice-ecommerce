package com.amimul.inventoryservice.repository;

import com.amimul.inventoryservice.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findBySkuCode(@NotNull(message = "Sku Code can not be null") String s);
}
