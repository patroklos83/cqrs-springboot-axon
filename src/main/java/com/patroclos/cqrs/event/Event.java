package com.patroclos.cqrs.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
	
	public Event() {
		this.created = LocalDateTime.now();
	}
	
    private final LocalDateTime created;
}
