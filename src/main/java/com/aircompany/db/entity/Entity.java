package com.aircompany.db.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.UUID;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
    public String id;
    public Entity(UUID id){
        this.id = id.toString();
    }
}
