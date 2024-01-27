package com.patroclos.cqrs.dto;

import com.patroclos.cqrs.event.StockAdjustmentType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryItemStockDTO {
    private Integer stockAdjustment;
    private StockAdjustmentType type;
}
