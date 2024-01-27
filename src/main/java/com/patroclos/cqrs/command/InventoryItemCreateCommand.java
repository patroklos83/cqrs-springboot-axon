package com.patroclos.cqrs.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemCreateCommand {

    @TargetAggregateIdentifier
    private UUID id;
    private Integer initialStock;
    private String name;
    private LocalDateTime created;
}
