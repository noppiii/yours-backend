package com.example.tes.coding.yours.payload.request.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateFormRequest {

    @NotBlank(message = "Nama form tidak boleh kosong.")
    private String name;

    private String description;

    private boolean limitOneResponse;

    @Size(min = 1, message = "Diperlukan setidaknya satu domain yang diizinkan.")
    private List<String> allowedDomains;

}
