package com.harshal.paymentLedgerSystem.common.exception;

import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;
import com.harshal.paymentLedgerSystem.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(
            InsufficientBalanceException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequest(
            DuplicateRequestException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        assert ex.getBindingResult() != null;
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, message, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR, "An unexpected error occurred", request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            ErrorCode errorCode,
            String message,
            HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(status.value())
                .errorCode(errorCode)
                .message(message)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(response);
    }
}