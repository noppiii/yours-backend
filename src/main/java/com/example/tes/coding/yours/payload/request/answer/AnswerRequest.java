package com.example.tes.coding.yours.payload.request.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AnswerRequest {

    @NotNull(message = "questionId wajib diisi")
    private Long questionId;

    @NotNull(message = "Nilai jawaban wajib diisi")
    @NotBlank(message = "Nilai jawaban tidak boleh kosong")
    private String value;
}



