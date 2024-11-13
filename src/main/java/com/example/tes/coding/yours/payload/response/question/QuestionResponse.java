package com.example.tes.coding.yours.payload.response.question;

import com.example.tes.coding.yours.model.Question;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionResponse {

    private final Long id;
    private final String name;
    private final String choiceType;
    private final String choices;
    private final boolean isRequired;
    private final Long formId;

    @Builder
    public QuestionResponse(Long id, String name, String choiceType, String choices, boolean isRequired, Long formId) {
        this.id = id;
        this.name = name;
        this.choiceType = choiceType;
        this.choices = choices;
        this.isRequired = isRequired;
        this.formId = formId;
    }

    public static QuestionResponse fromQuestion(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .name(question.getName())
                .choiceType(question.getChoiceType().name())
                .choices(question.getChoices())
                .isRequired(question.isRequired())
                .formId(question.getForm().getId())
                .build();
    }
}
