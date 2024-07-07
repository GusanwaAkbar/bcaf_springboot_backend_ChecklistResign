package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalHRIRDTO {
    private long id;
    private String exitInterview;
    private String approvalHRIRStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalHRIRDTO() {}

    public ApprovalHRIRDTO(long id, String exitInterview, String approvalHRIRStatus, String remarks,
                           Date approvedDate, Date createdDate, String approvedBy, String documentPath) {
        this.id = id;
        this.exitInterview = exitInterview;
        this.approvalHRIRStatus = approvalHRIRStatus;
        this.remarks = remarks;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.documentPath = documentPath;
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

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
