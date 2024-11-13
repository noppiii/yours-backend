package com.example.tes.coding.yours.service.auth.impl;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.exception.CustomException;
import com.example.tes.coding.yours.exception.DuplicateException;
import com.example.tes.coding.yours.exception.NotFoundException;
import com.example.tes.coding.yours.model.User;
import com.example.tes.coding.yours.payload.request.auth.LoginRequest;
import com.example.tes.coding.yours.payload.request.auth.RegisterRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.user.LoginResponse;
import com.example.tes.coding.yours.repository.UserRepository;
import com.example.tes.coding.yours.security.jwt.JwtToken;
import com.example.tes.coding.yours.security.jwt.JwtTokenProvider;
import com.example.tes.coding.yours.service.auth.AuthService;
import com.example.tes.coding.yours.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public CustomSuccessResponse<String> register(RegisterRequest registerRequest) throws IOException {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(it -> {
            throw new DuplicateException(ErrorCode.ALREADY_REGISTER);
        });

        User user = User.of(
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getName());

        userRepository.save(user);
        return new CustomSuccessResponse<>("200", "Berhasil melakukan registrasi", null);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication;

        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String authorities = authentication.getAuthorities().stream()
                .map(a -> "ROLE_" + a.getAuthority())
                .collect(Collectors.joining(","));
        JwtToken jwtToken = jwtTokenProvider.createJwtToken(loginRequest.getEmail(), authorities);

        return LoginResponse.of(jwtToken, user);
    }

    @Override
    public JwtToken refreshToken(String requestRefreshToken) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    @Override
    public CustomSuccessResponse<String> logout(String email) {
        jwtTokenUtils.deleteRefreshToken(email);
        return new CustomSuccessResponse<>("200", "Berhasil logout", null);
    }
}
