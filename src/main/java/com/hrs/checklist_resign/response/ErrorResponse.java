package com.hrs.checklist_resign.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    private int value;

    // Getters and setters

    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int value, String message) {
        this.value = value;
        this.message = message;
    }
}
