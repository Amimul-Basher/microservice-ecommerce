package com.amimul.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String inventoryNumber;
    private String wareHouse;
    private int totalQuantity;
    private Date date;
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<InventoryItem> inventoryItemList;
}
