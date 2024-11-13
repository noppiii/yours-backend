package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;

public class InvalidException extends CustomException {
    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
