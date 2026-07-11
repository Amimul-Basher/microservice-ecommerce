package com.amimul.inventoryservice.repository;

import com.amimul.inventoryservice.model.Inventory;
import com.amimul.inventoryservice.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
