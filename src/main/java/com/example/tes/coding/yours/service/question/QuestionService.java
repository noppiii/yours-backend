package com.example.tes.coding.yours.service.question;

import com.example.tes.coding.yours.payload.request.question.CreateQuestionRequest;
import com.example.tes.coding.yours.payload.response.CustomSuccessResponse;
import com.example.tes.coding.yours.payload.response.question.QuestionResponse;

public interface QuestionService {

    CustomSuccessResponse<QuestionResponse> createQuestion(String formSlug, CreateQuestionRequest request, String email);
    CustomSuccessResponse<String> deleteQuestion(String questionSlug, Long questionIid, String email);
}
