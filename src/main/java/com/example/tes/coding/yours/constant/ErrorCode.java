package com.example.tes.coding.yours.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ALREADY_REGISTER("User sudah ada.", HttpStatus.CONFLICT),
    EMAIL_NOT_FOUND("Email user tidak ditemukan.", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER("User tidak ditemukan.", HttpStatus.NOT_FOUND),
    FORM_NOT_FOUND("Form tidak ditemukan.", HttpStatus.NOT_FOUND),
    QUESTION_NOT_FOUND("Question tidak ditemukan.", HttpStatus.NOT_FOUND),
    EMPTY_REFRESH_TOKEN("RefreshToken diperlukan.", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("Permintaan tidak valid.", HttpStatus.BAD_REQUEST),
    RESPONSE_ALREADY_SUBMITTED("Anda hanya bisa mengirim satu kali.", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS("Password tidak sesuai", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("Anda tidak diizinkan masuk.", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_ALLOWED("Email tidak diizinkan.", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED("Silahkan login terlebih dahulu", HttpStatus.UNAUTHORIZED),
    INVALID_REQUEST("Pilihan diperlukan untuk jenis pilihan ini", HttpStatus.BAD_REQUEST),
    FORBIDDEN("Tidak memiliki izin akses.", HttpStatus.FORBIDDEN),
    INVALID_TYPE_TOKEN("Jenis token harus Bearer.", HttpStatus.UNAUTHORIZED),
    EMPTY_AUTHORITY("Informasi otorisasi diperlukan.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_ACCESS_TOKEN("AccessToken telah kedaluwarsa.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("AccessToken tidak valid.", HttpStatus.UNAUTHORIZED),
    EXPIRED_PERIOD_REFRESH_TOKEN("RefreshToken telah kedaluwarsa.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("RefreshToken tidak valid.", HttpStatus.UNAUTHORIZED),
    LOGOUTED_TOKEN("Token sudah diproses untuk logout.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
