package com.hrs.checklist_resign.dto;

public class ApprovalHRLearningDTO {
    private long id;
    private String pengecekanBiayaTraining;
    private String approvalHRLearningStatus;
    private String remarks;

    public ApprovalHRLearningDTO() {
    }

    public ApprovalHRLearningDTO(long id, String pengecekanBiayaTraining, String approvalHRLearningStatus, String remarks) {
        this.id = id;
        this.pengecekanBiayaTraining = pengecekanBiayaTraining;
        this.approvalHRLearningStatus = approvalHRLearningStatus;
        this.remarks = remarks;
    }

    // Getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
