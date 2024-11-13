package com.example.tes.coding.yours.service.form.impl;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.exception.CustomException;
import com.example.tes.coding.yours.model.AllowedDomain;
import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.User;
import com.example.tes.coding.yours.payload.request.form.CreateFormRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.DetailFormResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponse;
import com.example.tes.coding.yours.payload.response.form.GetFormsResponse;
import com.example.tes.coding.yours.repository.AllowedDomainRepository;
import com.example.tes.coding.yours.repository.FormRepository;
import com.example.tes.coding.yours.repository.UserRepository;
import com.example.tes.coding.yours.service.form.FormService;
import com.example.tes.coding.yours.util.SlugUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FormServiceImpl implements FormService {

    private final UserRepository userRepository;
    private final FormRepository formRepository;
    private final AllowedDomainRepository allowedDomainRepository;

    @Override
    public CustomSuccessResponse<FormResponse> createForm(CreateFormRequest createFormRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));

        String slug = SlugUtils.generateSlug(user.getName(), "form", createFormRequest.getName());
        int counter = 1;
        String originalSlug = slug;
        while (formRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + counter;
            counter++;
        }

        Form form = Form.builder()
                .name(createFormRequest.getName())
                .slug(slug)
                .description(createFormRequest.getDescription())
                .limitOneResponse(createFormRequest.isLimitOneResponse())
                .creator(user)
                .build();
        formRepository.save(form);

        List<AllowedDomain> allowedDomains = createFormRequest.getAllowedDomains().stream()
                .map(domain -> AllowedDomain.builder()
                        .form(form)
                        .domain(domain)
                        .build())
                .collect(Collectors.toList());
        allowedDomainRepository.saveAll(allowedDomains);

        FormResponse formResponse = FormResponse.readFormResponse(form);

        return new CustomSuccessResponse<>("200", "Berhasil membuat form", formResponse);
    }

    @Override
    public CustomSuccessResponse<GetFormsResponse> getForms(Pageable pageable, String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Page<Form> formPage = formRepository.findAll(pageable);
        GetFormsResponse getFormsResponse = GetFormsResponse.of(formPage);
        return new CustomSuccessResponse<>("200", "Berhasil mendapatkan semua data form", getFormsResponse);
    }

    @Override
    public CustomSuccessResponse<DetailFormResponse> detailForm(String formSlug, String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Form form = formRepository.findForm(formSlug)
                .orElseThrow(() -> new CustomException(ErrorCode.FORM_NOT_FOUND));
        DetailFormResponse detailFormResponse = DetailFormResponse.of(form);

        return new CustomSuccessResponse<>("200", "Berhasil mendapatkan data form", detailFormResponse);
    }

}
