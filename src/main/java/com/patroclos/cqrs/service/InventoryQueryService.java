package com.patroclos.cqrs.service;

import com.patroclos.businessobject.InventoryItemBO;
import com.patroclos.cqrs.entity.InventoryItem;
import com.patroclos.cqrs.event.Event;
import com.patroclos.cqrs.event.InventoryItemCreatedEvent;
import com.patroclos.cqrs.event.InventoryItemStockAdjustmentEvent;
import com.patroclos.cqrs.event.StockAdjustmentType;
import com.patroclos.cqrs.query.FindInventoryItemQuery;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.patroclos.util.*;

@Service
public class InventoryQueryService {
	
	@Autowired
	private QueryGateway queryGateway;
	@Autowired
	private EventStore eventStore;	
	@Autowired
	private InventoryItemBO inventoryItemBO;

	public CompletableFuture<InventoryItem> findById(String id, String date) {
		LocalDateTime dateTime = null;
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			dateTime = LocalDateTime.parse(date, formatter);
		}
		
		return this.queryGateway.query(
				new FindInventoryItemQuery(UUID.fromString(id), dateTime),
				ResponseTypes.instanceOf(InventoryItem.class)
				);
	}

	public List<Object> listEventsForItem(String id) {
		return this.eventStore
				.readEvents(ServiceUtils.formatUuid(id).toString(), 0)
				.asStream()
				.map(Message::getPayload)
				.collect(Collectors.toList());
	}

	/***
	 * Method that recreates the Inventory Item from the beginning
	 * @param id
	 * @return
	 */
	public InventoryItem handle(String id, LocalDateTime dateTime) {
		InventoryItem item = new InventoryItem();

		List<Object> events = listEventsForItem(id);
		for (Object event : events) {
			if (!(event instanceof Event)) {
				continue;
			}
			
			if (dateTime != null && ((Event)event).getCreated().isAfter(dateTime)) {
				continue;
			}
			
			if (event instanceof InventoryItemCreatedEvent) {
				InventoryItemCreatedEvent e = (InventoryItemCreatedEvent) event;
				item.setName(e.getName());
				item.setId(e.getId());
				item.setStock(e.getInitialStock());
				item.setCreated(e.getCreated());
			}
			else if (event instanceof InventoryItemStockAdjustmentEvent)
				handleEventStockReplay((InventoryItemStockAdjustmentEvent) event, item);
		}
		
		return item;
	}

	public void handleEventStockReplay(InventoryItemStockAdjustmentEvent event, InventoryItem item) {
		item.setCreated(event.getCreated());
		if (event.getType() == StockAdjustmentType.Add) {
			inventoryItemBO.addStock(item, event.getStockAdjustment());
		}
		else {
			inventoryItemBO.removeStock(item, event.getStockAdjustment());
		}
	}
}
