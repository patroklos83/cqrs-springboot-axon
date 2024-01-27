package com.patroclos.cqrs.event;

import lombok.Value;

import java.util.UUID;

@Value
public class InventoryItemStockAdjustmentEvent extends Event {

    private final UUID id;
    private final Integer stockAdjustment;
    private final StockAdjustmentType type;
}
