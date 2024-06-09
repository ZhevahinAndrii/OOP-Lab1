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
public class Brigade extends Entity{
    private String name;
    private boolean staticCrew;
    public Brigade(UUID id){
        super(id);
    }
}
