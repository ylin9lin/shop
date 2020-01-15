package com.lin.shopping.exception;

import org.springframework.http.ResponseEntity;

class ErrorResponse {
    private long code;

    private String message;

    private Object data;

    private ErrorResponse() {
    }

    public static ErrorResponse of(long code, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.code = code;
        errorResponse.message = message;
        return errorResponse;
    }

    public static ResponseEntity<ErrorResponse> generateResponse(BusinessException e) {
        String message = e.getMessage();
        ErrorResponse errorResponse = ErrorResponse.of(e.getError().getCode(), message);
        return new ResponseEntity<>(errorResponse, e.getError().getHttpStatus());
    }

    public static ResponseEntity<ErrorResponse> generateResponse(String message, BusinessError error) {
        ErrorResponse errorResponse = ErrorResponse.of(error.getCode(), message);
        return new ResponseEntity<ErrorResponse>(errorResponse, error.getHttpStatus());
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
