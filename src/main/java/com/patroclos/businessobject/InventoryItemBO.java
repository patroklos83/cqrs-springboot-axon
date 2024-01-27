package com.patroclos.businessobject;

import com.patroclos.cqrs.entity.InventoryItem;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemBO {

	public void addStock(InventoryItem item, int stockAdjustment) {
		item.setStock(item.getStock() + stockAdjustment);
	}

	public void removeStock(InventoryItem item, int stockAdjustment) {
		item.setStock(item.getStock() - stockAdjustment);
	}
}
