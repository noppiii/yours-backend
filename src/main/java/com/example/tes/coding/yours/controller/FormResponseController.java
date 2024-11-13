package com.example.tes.coding.yours.controller;

import com.example.tes.coding.yours.payload.request.form.CreateFormResponseRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponseResponse;
import com.example.tes.coding.yours.payload.response.form.GetFormsResponse;
import com.example.tes.coding.yours.service.form.FormResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "responses", description = "Response API")
@RestController
@RequestMapping("/api/v1/form")
@RequiredArgsConstructor
public class FormResponseController {

    private final FormResponseService formResponseService;

    @Operation(summary = "Create Response", description = "Submit response pada form")
    @PostMapping("/{formSlug}/responses")
    public ResponseEntity<CustomSuccessResponse<String>> submitFormResponse(@PathVariable String formSlug, @RequestBody @Valid CreateFormResponseRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<String> response = formResponseService.submitResponse(formSlug, request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "All Response", description = "Lihat semua data response dari form")
    @GetMapping("/{formSlug}/responses")
    public ResponseEntity<CustomSuccessResponse<List<FormResponseResponse>>> getAllResponse(@PathVariable String formSlug, @PageableDefault(size = 8) Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<List<FormResponseResponse>> response = formResponseService.getAllResponse(formSlug, pageable, email);
        return ResponseEntity.ok(response);
    }
}
