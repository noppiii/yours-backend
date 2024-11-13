package com.example.tes.coding.yours.payload.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "Email wajib diisi.")
    @Email(message = "Masukkan email sesuai format yang benar.")
    private String email;

    @NotBlank(message = "Kata sandi wajib diisi.")
    @Size(min = 5, max = 20, message = "Kata sandi harus terdiri dari minimal 8 dan maksimal 20 karakter.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Kata sandi harus menggunakan huruf besar, huruf kecil, angka, dan karakter khusus.")
    private String password;
}
