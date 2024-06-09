package com.aircompany.db.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crewmate extends Entity {
    public Crewmate(UUID id){
        super(id);
    }
    public static enum Qualification{
        STUART,
        PILOT,
        RADIO_OFFICER
    }
    private String name;
    private Qualification qualification;
    private String brigadeId;
}
