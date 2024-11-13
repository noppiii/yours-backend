package com.example.tes.coding.yours.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtToken {

    private final String accessToken;
    private final String refreshToken;

}
