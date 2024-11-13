package com.example.tes.coding.yours.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChoiceType {

    SHORT_ANSWER("Short Answer"),
    PARAGRAPH("Paragraph"),
    DATE("Date"),
    TIME("Time"),
    MULTIPLE_CHOICE("Multiple Choice"),
    DROPDOWN("Dropdown"),
    CHECKBOX("Checkbox");

    private final String value;

}
