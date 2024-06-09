package com.aircompany.db.entity;

import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Race extends Entity{
    private String departurePlace;
    private String arrivalPlace;
    private String departureTime;
    private String arrivalTime;
    private int passengers;
    private double luggageWeight;

    public Race(UUID id) {
        super(id);
    }
}
