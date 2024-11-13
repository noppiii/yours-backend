package com.example.tes.coding.yours.service.auth;

import com.example.tes.coding.yours.model.User;
import com.example.tes.coding.yours.payload.request.auth.LoginRequest;
import com.example.tes.coding.yours.payload.request.auth.RegisterRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.user.LoginResponse;
import com.example.tes.coding.yours.security.jwt.JwtToken;

import java.io.IOException;

public interface AuthService {

    CustomSuccessResponse<String> register(RegisterRequest registerRequest) throws IOException;
    LoginResponse login(LoginRequest loginRequest);
    JwtToken refreshToken(String requestRefreshToken);
    User getUserByEmail(String email);
    CustomSuccessResponse<String> logout(String email);
}
