package com.patroclos.cqrs.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.patroclos.cqrs.command.InventoryItemCommand;
import com.patroclos.cqrs.command.InventoryItemCreateCommand;
import com.patroclos.cqrs.dto.InventoryItemCreationDTO;
import com.patroclos.cqrs.dto.InventoryItemStockDTO;
import com.patroclos.cqrs.entity.InventoryItem;
import com.patroclos.util.ServiceUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryItemCommandService {
	
    private final CommandGateway commandGateway;

    public CompletableFuture<InventoryItem> createInventoryItem(InventoryItemCreationDTO itemCreateDTO) {
        return this.commandGateway.send(new InventoryItemCreateCommand(
                UUID.randomUUID(),
                itemCreateDTO.getInitialStock(),
                itemCreateDTO.getName(),
                LocalDateTime.now()
        ));
    }

    public CompletableFuture<String> incrementInventoryItemStock(String id, InventoryItemStockDTO inventoryItemStockDTO) {
        return this.commandGateway.send(new InventoryItemCommand(
        		ServiceUtils.formatUuid(id),
                inventoryItemStockDTO.getStockAdjustment(),
                inventoryItemStockDTO.getType(),
                LocalDateTime.now()
        ));
    }
}
