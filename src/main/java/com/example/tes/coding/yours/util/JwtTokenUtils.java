package com.example.tes.coding.yours.util;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.exception.BadRequestException;
import com.example.tes.coding.yours.service.global.InMemoryCacheService;
import com.example.tes.coding.yours.service.global.impl.InMemoryCacheServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final InMemoryCacheService inMemoryCacheService;

    @Value("${spring.security.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    public static String extractBearerToken(String token) {
        if (token != null) {
            if (!token.startsWith("Bearer")) throw new BadRequestException(ErrorCode.INVALID_TYPE_TOKEN);
            return token.split(" ")[1].trim();
        }
        return null;
    }

    public boolean isLogin(String email) {
        return inMemoryCacheService.getData("Login_" + email) != null;
    }

    public void setRefreshToken(String username, String refreshToken) {
        inMemoryCacheService.setData("Login_" + username, refreshToken, refreshExpirationTime, TimeUnit.SECONDS);
    }

    public void updateRefreshToken(String username, String refreshToken) {
        setRefreshToken(username, refreshToken);
    }

    public void deleteRefreshToken(String username) {
        inMemoryCacheService.deleteData("Login_" + username);
    }

    public String getRefreshToken(String username) {
        Object token = inMemoryCacheService.getData("Login_" + username);
        return token != null ? token.toString() : null;
    }
}
