package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

