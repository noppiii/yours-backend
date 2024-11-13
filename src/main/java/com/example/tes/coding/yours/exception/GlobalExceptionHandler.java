package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.payload.response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleCustomException(CustomException ex) {
        CustomErrorResponse<Object> errorResponse = ex.toErrorResponse();
        return ResponseEntity
                .status(HttpStatus.valueOf(Integer.parseInt(errorResponse.getStatusCode())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = String.format("Metode %s tidak diizinkan untuk endpoint ini.", ex.getMethod());
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), errorMessage, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleBadRequestException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage());
        });

        String contentMessage = "Body permintaan atau parameter tidak valid.";
        String errorMessage = "Terjadi kesalahan validasi.";

        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, contentMessage, errors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleNotFoundException(NoSuchElementException ex) {
        String errorMessage = String.format("Sumber daya dengan ID %s tidak ditemukan.", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage, ex.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleUnauthorizedException(UnauthorizedException ex) {
        String errorMessage = String.format("Akses tidak sah: %s", ErrorCode.UNAUTHORIZED.getMessage());
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("error", List.of(ErrorCode.UNAUTHORIZED.getMessage()));
        errors.put("details", List.of(ex.getMessage()));
        return buildErrorResponse(ErrorCode.UNAUTHORIZED.getStatus().value(), errorMessage, ex.getMessage(), errors);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleForbiddenException(ForbiddenException ex) {
        String errorMessage = String.format("Dilarang: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN.value(), errorMessage, ex.getMessage(), null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleConflictException(ConflictException ex) {
        String errorMessage = String.format("Terjadi konflik: %s", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT.value(), errorMessage, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse<Object>> handleInternalServerErrorException(Exception ex) {
        String errorMessage = "Terjadi kesalahan yang tidak terduga.";
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage, ex.getMessage(), null);
    }

    private ResponseEntity<CustomErrorResponse<Object>> buildErrorResponse(int statusCode, String message, String content, Map<String, List<String>> errors) {
        CustomErrorResponse<Object> response = new CustomErrorResponse<>(
                String.valueOf(statusCode),
                message,
                content,
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }
}

