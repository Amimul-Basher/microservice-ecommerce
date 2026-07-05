package com.amimul.inventoryservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String skuCode;
    private int quantity;
    private String position;
    @ManyToOne
    @JoinColumn(name= "inventory_id")
    @JsonIgnore
    private Inventory inventory;
}
