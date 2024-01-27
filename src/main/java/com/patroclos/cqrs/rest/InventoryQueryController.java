package com.patroclos.cqrs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patroclos.cqrs.entity.InventoryItem;
import com.patroclos.cqrs.service.InventoryQueryService;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryQueryController {

	@Autowired
    private InventoryQueryService inventoryQueryService;

    @GetMapping("/{id}")
    public CompletableFuture<InventoryItem> findById(
    		@PathVariable("id") String id, 
    		@Parameter(
                    name =  "dateTime",
                    description  = "Fetch item state up until this specific date and time. (yyyy-MM-dd HH:mm)",
                    example = "2040-01-01 10:00",
                    required = false)
    		@RequestParam(name = "dateTime", required = false) String dateTime) {
        return this.inventoryQueryService.findById(id, dateTime);
    }
   

    @GetMapping("/{id}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "id") String id) {
        return this.inventoryQueryService.listEventsForItem(id);
    }
}
