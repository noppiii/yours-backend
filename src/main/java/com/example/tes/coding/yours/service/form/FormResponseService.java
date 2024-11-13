package com.example.tes.coding.yours.service.form;

import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.Question;
import com.example.tes.coding.yours.payload.request.form.CreateFormResponseRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FormResponseService {

    CustomSuccessResponse<String> submitResponse(String formSlug, CreateFormResponseRequest request, String email);
    CustomSuccessResponse<List<FormResponseResponse>> getAllResponse(String formSlug, Pageable pageable, String email);
    boolean isEmailAllowedForForm(Form form, String email);
    Question findQuestionById(Long questionId);
}
