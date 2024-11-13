package com.example.tes.coding.yours.exception;

import com.example.tes.coding.yours.constant.ErrorCode;

public class DuplicateException extends CustomException {
    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
