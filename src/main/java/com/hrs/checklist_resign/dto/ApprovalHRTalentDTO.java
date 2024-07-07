package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalHRTalentDTO {
    private long id;
    private String pengecekanBiaya;
    private String approvalHRTalentStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalHRTalentDTO() {}

    public ApprovalHRTalentDTO(long id, String pengecekanBiaya, String approvalHRTalentStatus, String remarks,
                               Date approvedDate, Date createdDate, String approvedBy, String documentPath) {
        this.id = id;
        this.pengecekanBiaya = pengecekanBiaya;
        this.approvalHRTalentStatus = approvalHRTalentStatus;
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

    public String getPengecekanBiaya() {
        return pengecekanBiaya;
    }

    public void setPengecekanBiaya(String pengecekanBiaya) {
        this.pengecekanBiaya = pengecekanBiaya;
    }

    public String getApprovalHRTalentStatus() {
        return approvalHRTalentStatus;
    }

    public void setApprovalHRTalentStatus(String approvalHRTalentStatus) {
        this.approvalHRTalentStatus = approvalHRTalentStatus;
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
