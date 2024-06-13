package com.hrs.checklist_resign.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    // Getters and setters

    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}
