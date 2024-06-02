package com.hrs.checklist_resign.response;
import java.time.LocalDateTime;

public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String errorDetails; // Add this field

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data, boolean success, String message, int status) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, int status, String errorDetails) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.errorDetails = errorDetails;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
