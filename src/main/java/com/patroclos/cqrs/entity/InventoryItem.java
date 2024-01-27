package com.patroclos.cqrs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryItem {
	
//    @Id
//    private UUID id;
//    private String name;
//    private Integer stock;
    
	@AggregateIdentifier
    @Id
	protected UUID id;
    protected Integer stock;
    protected String name;
    protected LocalDateTime created;
}
