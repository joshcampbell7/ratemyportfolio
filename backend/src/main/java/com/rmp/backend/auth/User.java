package com.rmp.backend.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userId;
    private String email;
    private String password;

    public User(String email, String userName, String firstName, String lastName, String password) {
        this.userId = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
    }

}