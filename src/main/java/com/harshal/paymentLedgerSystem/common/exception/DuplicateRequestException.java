package com.harshal.paymentLedgerSystem.common.exception;

import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;

public class DuplicateRequestException extends BusinessException {
    public DuplicateRequestException(String message) {
        super(ErrorCode.DUPLICATE_REQUEST, message);
    }
}
