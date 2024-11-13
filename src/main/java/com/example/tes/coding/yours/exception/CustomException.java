package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;
import com.example.tes.coding.yours.payload.response.CustomErrorResponse;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomErrorResponse<Object> toErrorResponse() {
        Map<String, List<String>> errors = new HashMap<>();

        errors.put("error", List.of(this.errorCode.getMessage()));
        errors.put("details", List.of(super.getMessage()));

        return new CustomErrorResponse<>(
                String.valueOf(this.errorCode.getStatus().value()),
                this.errorCode.getMessage(),
                this.errorCode.name(),
                errors
        );
    }
}


