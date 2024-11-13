package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
