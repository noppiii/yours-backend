package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
