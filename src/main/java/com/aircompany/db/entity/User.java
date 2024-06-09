package com.aircompany.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Entity{
    private String login;
    private String email;
    private String role;

    public User(String id, String login, String email, String role){
        super(id);
        this.login = login;
        this.email = email;
        this.role = role;
    }
}
