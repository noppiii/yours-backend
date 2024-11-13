package com.example.tes.coding.yours.service.form;

import com.example.tes.coding.yours.payload.request.form.CreateFormRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.DetailFormResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponse;
import com.example.tes.coding.yours.payload.response.form.GetFormsResponse;
import org.springframework.data.domain.Pageable;

public interface FormService {

    CustomSuccessResponse<FormResponse> createForm(CreateFormRequest createFormRequest, String email);
    CustomSuccessResponse<GetFormsResponse> getForms(Pageable pageable, String email);
    CustomSuccessResponse<DetailFormResponse> detailForm(String formSlug, String email);
}
