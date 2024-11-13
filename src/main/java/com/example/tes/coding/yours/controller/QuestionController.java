package com.example.tes.coding.yours.controller;

import com.example.tes.coding.yours.payload.request.form.CreateFormRequest;
import com.example.tes.coding.yours.payload.request.question.CreateQuestionRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponse;
import com.example.tes.coding.yours.payload.response.question.QuestionResponse;
import com.example.tes.coding.yours.service.question.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "questions", description = "Question API")
@RestController
@RequestMapping("/api/v1/form")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "Create Question", description = "Buat pertanyaan dari form")
    @PostMapping("/{formSlug}/question")
    public ResponseEntity<CustomSuccessResponse<QuestionResponse>> createQuestion(@PathVariable String formSlug, @RequestBody @Valid CreateQuestionRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<QuestionResponse> response = questionService.createQuestion(formSlug, request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete Question", description = "Hapus pertanyaan dari form")
    @DeleteMapping("/{formSlug}/question/{questionId}")
    public ResponseEntity<CustomSuccessResponse<String>> deleteQuestion(@PathVariable String formSlug, @PathVariable Long questionId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<String> response = questionService.deleteQuestion(formSlug, questionId, email);
        return ResponseEntity.ok(response);
    }
}
