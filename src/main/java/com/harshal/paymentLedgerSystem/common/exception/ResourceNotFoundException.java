package com.harshal.paymentLedgerSystem.common.exception;

import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }
}
