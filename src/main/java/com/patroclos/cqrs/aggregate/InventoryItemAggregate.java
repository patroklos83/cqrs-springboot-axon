package com.patroclos.cqrs.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import com.patroclos.businessobject.InventoryItemBO;
import com.patroclos.cqrs.command.InventoryItemCommand;
import com.patroclos.cqrs.command.InventoryItemCreateCommand;
import com.patroclos.cqrs.entity.InventoryItem;
import com.patroclos.cqrs.event.InventoryItemCreatedEvent;
import com.patroclos.cqrs.event.InventoryItemStockAdjustmentEvent;
import com.patroclos.cqrs.event.StockAdjustmentType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate(snapshotTriggerDefinition = "inventoryAggregateSnapshotTriggerDefinition")
public class InventoryItemAggregate extends InventoryItem {
	
	@Autowired
	private InventoryItemBO inventoryItemBO;

    @CommandHandler
    public InventoryItemAggregate(InventoryItemCreateCommand command) {
        AggregateLifecycle.apply(
                new InventoryItemCreatedEvent(
                        command.getId(),
                        command.getInitialStock(),
                        command.getName()
                )
        );
    }

    @EventSourcingHandler
    public void on(InventoryItemCreatedEvent event) {
        super.id = event.getId();
        super.name = event.getName();
        super.stock = event.getInitialStock();
        super.created = event.getCreated();
    }

    @CommandHandler
    public void handle(InventoryItemCommand command) {
        AggregateLifecycle.apply(
                new InventoryItemStockAdjustmentEvent(
                        command.getId(),
                        command.getStock(),
                        command.getType()
                )
        );
    }

    @EventSourcingHandler
    public void on(InventoryItemStockAdjustmentEvent event) {
    	if (event.getType() == StockAdjustmentType.Add) {
    		inventoryItemBO.addStock(this, event.getStockAdjustment());
    	}
    	else {
    		if (super.stock - event.getStockAdjustment() < 0) {
              throw new IllegalArgumentException("Cannot deduct from stock if stock if stock adjustment > current stock");
          }
          inventoryItemBO.removeStock(this, event.getStockAdjustment());
    	}
    		
    }
}
