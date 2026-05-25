package com.harshal.paymentLedgerSystem.common.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timeStamp;
    private final int status;
    private final ErrorCode errorCode;
    private final String message;
    private final String path;
}
