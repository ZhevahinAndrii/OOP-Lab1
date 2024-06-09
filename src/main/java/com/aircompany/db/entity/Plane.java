package com.aircompany.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plane extends Entity{
    private String model;
    private int passengerSeats;
    private double maxLuggage;
    private int maxFlightInMins;

    public Plane(UUID id) {
        super(id);
    }

}
