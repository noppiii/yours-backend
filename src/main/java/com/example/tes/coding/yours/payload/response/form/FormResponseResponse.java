package com.example.tes.coding.yours.payload.response.form;

import com.example.tes.coding.yours.model.Answer;
import com.example.tes.coding.yours.model.Response;
import com.example.tes.coding.yours.payload.response.user.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FormResponseResponse {

    private final String date;
    private final UserResponse user;
    private final Map<String, String> answers;

    @Builder
    public FormResponseResponse(String date, UserResponse user, Map<String, String> answers) {
        this.date = date;
        this.user = user;
        this.answers = answers;
    }

    public static FormResponseResponse fromResponse(Response response) {
        UserResponse userResponse = UserResponse.fromUser(response.getUser());
        Map<String, String> answersMap = new HashMap<>();

        for (Answer answer : response.getAnswers()) {
            answersMap.put(answer.getQuestion().getName(), answer.getValue());
        }

        return FormResponseResponse.builder()
                .date(response.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .user(userResponse)
                .answers(answersMap)
                .build();
    }
}
