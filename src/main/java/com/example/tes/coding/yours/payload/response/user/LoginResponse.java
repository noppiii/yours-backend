package com.example.tes.coding.yours.payload.response.user;

import com.example.tes.coding.yours.model.User;
import com.example.tes.coding.yours.security.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final UserInfo user;
    private final String tokenType;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    private LoginResponse(UserInfo user, String accessToken, String refreshToken) {
        this.user = user;
        this.tokenType = "Bearer";
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginResponse of(JwtToken jwtToken, User user) {
        return LoginResponse.builder()
                .user(new UserInfo(user.getName(), user.getEmail(), user.getRole().name()))
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfo {
        private final String name;
        private final String email;
        private final String role;
    }
}
