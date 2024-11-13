package com.example.tes.coding.yours.service.form.impl;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.exception.CustomException;
import com.example.tes.coding.yours.model.*;
import com.example.tes.coding.yours.payload.request.form.CreateFormResponseRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.form.FormResponseResponse;
import com.example.tes.coding.yours.repository.*;
import com.example.tes.coding.yours.service.form.FormResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class FormResponseServiceImpl implements FormResponseService {

    private final FormRepository formRepository;
    private final UserRepository userRepository;
    private final FormResponseRepository responseRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public CustomSuccessResponse<String> submitResponse(String formSlug, CreateFormResponseRequest request, String email) {
        Form form = formRepository.findFormWithAllowedDomains(formSlug)
                .orElseThrow(() -> new CustomException(ErrorCode.FORM_NOT_FOUND));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));

        if (form.isLimitOneResponse()) {
            boolean hasResponded = responseRepository.existsByFormAndUser(form, user);
            if (hasResponded) {
                throw new CustomException(ErrorCode.RESPONSE_ALREADY_SUBMITTED);
            }
        }

        if (!isEmailAllowedForForm(form, email)) {
            throw new CustomException(ErrorCode.EMAIL_NOT_ALLOWED);
        }

        Response response = Response.builder()
                .form(form)
                .user(user)
                .date(LocalDateTime.now())
                .answers(new ArrayList<>())
                .build();

        List<Answer> answers = request.getAnswers().stream()
                .map(answerRequest -> new Answer(answerRequest.getValue(), findQuestionById(answerRequest.getQuestionId()), response))
                .collect(Collectors.toList());

        response.setAnswers(answers);
        responseRepository.save(response);

        return new CustomSuccessResponse<>("200", "Berhasil mengirimkan respons", null);
    }

    @Override
    public CustomSuccessResponse<List<FormResponseResponse>> getAllResponse(String formSlug, Pageable pageable, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));
        Form form = formRepository.findFormWithAllowedDomains(formSlug)
                .orElseThrow(() -> new CustomException(ErrorCode.FORM_NOT_FOUND));

        if (!isEmailAllowedForForm(form, email)) {
            throw new CustomException(ErrorCode.EMAIL_NOT_ALLOWED);
        }

        Page<Response> responsePage = responseRepository.findByForm(form, pageable);

        List<FormResponseResponse> formResponses = responsePage.getContent().stream()
                .map(FormResponseResponse::fromResponse)
                .collect(Collectors.toList());

        return new CustomSuccessResponse<>("200", "Berhasil mengambil daftar respons", formResponses);
    }



    @Override
    public boolean isEmailAllowedForForm(Form form, String email) {
        if (form.getAllowedDomains().isEmpty()) {
            return true;
        }

        String userDomain = email.substring(email.indexOf('@') + 1);
        return form.getAllowedDomains().stream()
                .anyMatch(domain -> domain.getDomain().equalsIgnoreCase(userDomain));
    }

    @Override
    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }
}
