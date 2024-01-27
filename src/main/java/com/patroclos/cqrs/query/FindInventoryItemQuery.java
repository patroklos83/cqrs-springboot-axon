package com.patroclos.cqrs.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindInventoryItemQuery {
    private UUID id;
    private LocalDateTime dateTime;
}
