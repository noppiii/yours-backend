package com.example.tes.coding.yours.payload.request.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateQuestionRequest {

    @NotBlank(message = "Nama pertanyaan tidak boleh kosong.")
    private String name;

    @NotBlank(message = "Tipe pilihan tidak boleh kosong.")
    @Pattern(regexp = "short answer|paragraph|date|multiple choice|dropdown|checkboxes",
            message = "Tipe pilihan hanya boleh berupa: short answer, paragraph, date, multiple choice, dropdown, atau checkboxes.")
    private String choiceType;

    @Size(min = 1, message = "Diperlukan setidaknya satu pilihan.")
    private List<String> choices;

    @NotNull(message = "Status pertanyaan wajib diisi.")
    private Boolean isRequired;

}

