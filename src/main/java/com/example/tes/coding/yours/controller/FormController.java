package com.example.tes.coding.yours.controller;

import com.example.tes.coding.yours.payload.request.form.CreateFormRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.DetailFormResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponse;
import com.example.tes.coding.yours.payload.response.form.GetFormsResponse;
import com.example.tes.coding.yours.service.form.FormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "forms", description = "Form API")
@RestController
@RequestMapping("/api/v1/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @Operation(summary = "Create Form", description = "Buat data form")
    @PostMapping
    public ResponseEntity<CustomSuccessResponse<FormResponse>> createForm(@RequestBody @Valid CreateFormRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<FormResponse> response = formService.createForm(request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "All Form", description = "Get semua data form")
    @GetMapping
    public ResponseEntity<CustomSuccessResponse<GetFormsResponse>> getForms(@PageableDefault(size = 8) Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<GetFormsResponse> response = formService.getForms(pageable, email);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Detail Form", description = "Lihat detai data form")
    @GetMapping("/{formSlug}")
    public ResponseEntity<CustomSuccessResponse<DetailFormResponse>> readPost(@PathVariable String formSlug) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomSuccessResponse<DetailFormResponse> response = formService.detailForm(formSlug, email);
        return ResponseEntity.ok(response);
    }
}
