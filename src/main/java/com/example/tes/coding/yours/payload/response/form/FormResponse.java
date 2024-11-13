package com.example.tes.coding.yours.payload.response.form;

import com.example.tes.coding.yours.model.Form;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FormResponse {

    private final Long id;
    private final String name;
    private final String slug;
    private final String description;
    private final boolean limitOneResponse;
    private final Long creatorId;

    @Builder
    public FormResponse(Long id, String name, String slug, String description, boolean limitOneResponse, Long creatorId) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.limitOneResponse = limitOneResponse;
        this.creatorId = creatorId;
    }

    public static FormResponse readFormResponse(Form form) {
        return FormResponse.builder()
                .id(form.getId())
                .name(form.getName())
                .slug(form.getSlug())
                .description(form.getDescription())
                .limitOneResponse(form.isLimitOneResponse())
                .creatorId(form.getCreator().getId())
                .build();
    }
}

