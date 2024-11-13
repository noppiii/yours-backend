package com.example.tes.coding.yours.controller;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.exception.BadRequestException;
import com.example.tes.coding.yours.payload.request.auth.LoginRequest;
import com.example.tes.coding.yours.payload.request.auth.RegisterRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.user.LoginResponse;
import com.example.tes.coding.yours.security.jwt.JwtToken;
import com.example.tes.coding.yours.service.auth.AuthService;
import com.example.tes.coding.yours.util.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "users", description = "Users API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register User", description = "Daftar dengan memasukkan alamat email, password, dan nama Anda.")
    @PostMapping(value = "/register")
    public ResponseEntity<CustomSuccessResponse<String>> register(@RequestBody @Valid RegisterRequest registerRequest) throws IOException {
        CustomSuccessResponse<String> response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login", description = "Masukkan email dan kata sandi untuk login.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "refreshToken", description = "Menerbitkan ulang refreshToken yang sudah kadaluwarsa.")
    @GetMapping("/refresh")
    public JwtToken rotateToken(HttpServletRequest request) {
        String refreshToken = JwtTokenUtils.extractBearerToken(request.getHeader("refreshToken"));

        if (refreshToken.isBlank()) {
            throw new BadRequestException(ErrorCode.EMPTY_REFRESH_TOKEN);
        }
        return authService.refreshToken(refreshToken);
    }

    @Operation(summary = "Logout", description = "Logout akun saat ini.")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<CustomSuccessResponse<String>> logoutUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(authService.logout(username));
    }
}
