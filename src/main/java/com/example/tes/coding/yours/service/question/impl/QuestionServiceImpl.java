package com.example.tes.coding.yours.service.question.impl;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.exception.CustomException;
import com.example.tes.coding.yours.model.ChoiceType;
import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.Question;
import com.example.tes.coding.yours.model.User;
import com.example.tes.coding.yours.payload.request.question.CreateQuestionRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.question.QuestionResponse;
import com.example.tes.coding.yours.repository.FormRepository;
import com.example.tes.coding.yours.repository.QuestionRepository;
import com.example.tes.coding.yours.repository.UserRepository;
import com.example.tes.coding.yours.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public CustomSuccessResponse<QuestionResponse> createQuestion(String formSlug, CreateQuestionRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));

        if (request.getChoiceType().equals("multiple choice") || request.getChoiceType().equals("dropdown") || request.getChoiceType().equals("checkboxes")) {
            if (request.getChoices() == null || request.getChoices().isEmpty()) {
                throw new CustomException(ErrorCode.INVALID_REQUEST);
            }
        }

        Form form = formRepository.findForm(formSlug)
                .orElseThrow(() -> new CustomException(ErrorCode.FORM_NOT_FOUND));

        Question question = Question.builder()
                .name(request.getName())
                .choiceType(ChoiceType.valueOf(request.getChoiceType().toUpperCase().replace(" ", "_"))) // Mapping to enum
                .choices(request.getChoices() != null ? String.join(",", request.getChoices()) : null)
                .isRequired(request.getIsRequired())
                .form(form)
                .build();

        questionRepository.save(question);
        QuestionResponse response = QuestionResponse.fromQuestion(question);

        return new CustomSuccessResponse<>("200", "Berhasil membuat pertanyaan", response);
    }

    @Override
    public CustomSuccessResponse<String> deleteQuestion(String formSlug, Long questionId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHENTICATED));

        Form form = formRepository.findForm(formSlug)
                .orElseThrow(() -> new CustomException(ErrorCode.FORM_NOT_FOUND));

        Question question = questionRepository.findByIdAndForm(questionId, form)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

        questionRepository.delete(question);
        return new CustomSuccessResponse<>("200", "Berhasil menghapus question", null);
    }
}

