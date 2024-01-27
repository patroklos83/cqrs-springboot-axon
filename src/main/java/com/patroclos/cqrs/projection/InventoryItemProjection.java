package com.patroclos.cqrs.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.patroclos.cqrs.entity.InventoryItem;
import com.patroclos.cqrs.event.InventoryItemCreatedEvent;
import com.patroclos.cqrs.event.InventoryItemStockAdjustmentEvent;
import com.patroclos.cqrs.query.FindInventoryItemQuery;
import com.patroclos.cqrs.repository.InventoryItemRepository;
import com.patroclos.cqrs.service.InventoryQueryService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class InventoryItemProjection {

    private final InventoryItemRepository repository;
    private final QueryUpdateEmitter updateEmitter;
    private final EventStore eventStore;
    @Autowired
    private InventoryQueryService inventoryQueryService;


    @EventHandler
    public void on(InventoryItemCreatedEvent event) {
        log.debug("Handling an Inventory Item creation command {}", event.getId());
        InventoryItem item = new InventoryItem(
                event.getId(),
                event.getInitialStock(),
                event.getName(),
                event.getCreated()
        );
        this.repository.save(item);
    }

    @EventHandler
    public void on(InventoryItemStockAdjustmentEvent event) {
        log.debug("Handling an inventory stock adjustment (invrease in stock) command {}", event.getId());
        Optional<InventoryItem> optionalItem = this.repository.findById(event.getId());
        if (optionalItem.isEmpty()) {
        	throw new NullPointerException("Could not find Inventory Item with id: [ " + event.getId() + " ]");
        }
        InventoryItem item = optionalItem.get();
        inventoryQueryService.handleEventStockReplay(event, item);
        this.repository.save(item);
    }

    @QueryHandler
    public InventoryItem handle(FindInventoryItemQuery query) {
        log.debug("Handling FindInventoryItemQuery query: {}", query);
        if (query.getDateTime() != null) {
        	InventoryItem item = inventoryQueryService.handle(query.getId().toString(), query.getDateTime());
        	return item;
        }
        
        return this.repository.findById(query.getId()).orElse(null);
    }
}
