package com.harshal.paymentLedgerSystem.common.exception;

import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException(String message) {
        super(ErrorCode.INSUFFICIENT_BALANCE, message);
    }

}
