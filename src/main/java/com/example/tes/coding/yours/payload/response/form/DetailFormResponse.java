package com.example.tes.coding.yours.payload.response.form;

import com.example.tes.coding.yours.model.AllowedDomain;
import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.Question;
import com.example.tes.coding.yours.payload.response.question.QuestionResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class DetailFormResponse {

    private Long formId;
    private String name;
    private String slug;
    private String description;
    private boolean limitOneResponse;
    private Long creatorId;
    private String creatorName;
    private List<String> allowedDomains;
    private List<QuestionResponse> questions;

    public static DetailFormResponse of(Form form) {
        return DetailFormResponse.builder()
                .formId(form.getId())
                .name(form.getName())
                .slug(form.getSlug())
                .description(form.getDescription())
                .limitOneResponse(form.isLimitOneResponse())
                .creatorId(form.getCreator().getId())
                .creatorName(form.getCreator().getName())
                .allowedDomains(form.getAllowedDomains().stream()
                        .map(AllowedDomain::getDomain)
                        .collect(Collectors.toList()))
                .questions(form.getQuestions().stream()
                        .map(QuestionResponse::fromQuestion)
                        .collect(Collectors.toList()))
                .build();
    }
}

