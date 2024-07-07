package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalHRLearningDTO {
    private long id;
    private String pengecekanBiayaTraining;
    private String approvalHRLearningStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalHRLearningDTO() {}

    public ApprovalHRLearningDTO(long id, String pengecekanBiayaTraining, String approvalHRLearningStatus, String remarks,
                                 Date approvedDate, Date createdDate, String approvedBy, String documentPath) {
        this.id = id;
        this.pengecekanBiayaTraining = pengecekanBiayaTraining;
        this.approvalHRLearningStatus = approvalHRLearningStatus;
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

    public String getPengecekanBiayaTraining() {
        return pengecekanBiayaTraining;
    }

    public void setPengecekanBiayaTraining(String pengecekanBiayaTraining) {
        this.pengecekanBiayaTraining = pengecekanBiayaTraining;
    }

    public String getApprovalHRLearningStatus() {
        return approvalHRLearningStatus;
    }

    public void setApprovalHRLearningStatus(String approvalHRLearningStatus) {
        this.approvalHRLearningStatus = approvalHRLearningStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
