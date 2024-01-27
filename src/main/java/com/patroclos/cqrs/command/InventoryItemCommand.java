package com.patroclos.cqrs.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.patroclos.cqrs.event.StockAdjustmentType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemCommand {

    @TargetAggregateIdentifier
    private UUID id;
    private Integer stock;
    private StockAdjustmentType type;
    private LocalDateTime created;
}
