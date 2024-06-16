package com.hrs.checklist_resign.dto;

public class ApprovalHRTalentDTO {
    private long id;
    private String pengecekanBiaya;
    private String approvalHRTalentStatus;
    private String remarks;

    public ApprovalHRTalentDTO() {
    }

    public ApprovalHRTalentDTO(long id, String pengecekanBiaya, String approvalHRTalentStatus, String remarks) {
        this.id = id;
        this.pengecekanBiaya = pengecekanBiaya;
        this.approvalHRTalentStatus = approvalHRTalentStatus;
        this.remarks = remarks;
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
}
