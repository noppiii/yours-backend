package com.example.tes.coding.yours.payload.request.form;

import com.example.tes.coding.yours.payload.request.answer.AnswerRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateFormResponseRequest {

    @NotEmpty(message = "Jawaban wajib diisi")
    private List<@Valid AnswerRequest> answers;
}
