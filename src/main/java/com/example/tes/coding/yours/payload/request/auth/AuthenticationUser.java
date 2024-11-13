package com.example.tes.coding.yours.payload.request.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthenticationUser extends org.springframework.security.core.userdetails.User {

    private final com.example.tes.coding.yours.model.User userDetails;

    public AuthenticationUser(com.example.tes.coding.yours.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.userDetails = user;
    }
}
