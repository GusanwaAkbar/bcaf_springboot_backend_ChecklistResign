package com.hrs.checklist_resign.dto;

public class ApprovalHRIRDTO {
    private long id;
    private String exitInterview;
    private String approvalHRIRStatus;
    private String remarks;

    public ApprovalHRIRDTO() {
    }

    public ApprovalHRIRDTO(long id, String exitInterview, String approvalHRIRStatus, String remarks) {
        this.id = id;
        this.exitInterview = exitInterview;
        this.approvalHRIRStatus = approvalHRIRStatus;
        this.remarks = remarks;
    }

    // Getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExitInterview() {
        return exitInterview;
    }

    public void setExitInterview(String exitInterview) {
        this.exitInterview = exitInterview;
    }

    public String getApprovalHRIRStatus() {
        return approvalHRIRStatus;
    }

    public void setApprovalHRIRStatus(String approvalHRIRStatus) {
        this.approvalHRIRStatus = approvalHRIRStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
