package com.patroclos.cqrs.rest;


import com.patroclos.cqrs.dto.InventoryItemCreationDTO;
import com.patroclos.cqrs.dto.InventoryItemStockDTO;
import com.patroclos.cqrs.entity.InventoryItem;
import com.patroclos.cqrs.service.InventoryItemCommandService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/inventoryitems")
@AllArgsConstructor
public class InventoryItemCommandController {
	
    private final InventoryItemCommandService inventoryItemCommandService;

    @PostMapping(value = "/create")
    public CompletableFuture<InventoryItem> createItem(@RequestBody InventoryItemCreationDTO itemCreateDTO) {
        return this.inventoryItemCommandService.createInventoryItem(itemCreateDTO);
    }

    @PutMapping(value = "/stockadjustment/{id}")
    public CompletableFuture<String> stockAdjustment(@PathVariable(value = "id") String id,
                                                          @RequestBody InventoryItemStockDTO inventoryItemStockDTO) {
        return this.inventoryItemCommandService.incrementInventoryItemStock(id, inventoryItemStockDTO);
    }
}
