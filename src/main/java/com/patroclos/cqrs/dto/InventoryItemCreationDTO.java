package com.patroclos.cqrs.dto;

import lombok.Value;

@Value
public class InventoryItemCreationDTO {
    private final Integer initialStock;
    private final String name;
}
