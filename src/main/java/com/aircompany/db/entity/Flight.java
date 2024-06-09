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
public class Flight extends Entity{
    private String raceId;
    private String brigadeId;
    private String planeId;

    public Flight(UUID id) {
        super(id);
    }
}
