package com.patroclos.cqrs.event;

import lombok.Data;

import java.util.UUID;

@Data
public class InventoryItemCreatedEvent extends Event {
    private final UUID id;
    private final Integer initialStock;
    private final String name;
}
