package com.hrs.checklist_resign.dto;

public class ApprovalTreasuryDTO {
    private long id;
    private String biayaAdvance;
    private String blokirFleet;
    private String approvalTreasuryStatus;
    private String remarks;

    public ApprovalTreasuryDTO() {
    }

    public ApprovalTreasuryDTO(long id, String biayaAdvance, String blokirFleet, String approvalTreasuryStatus, String remarks) {
        this.id = id;
        this.biayaAdvance = biayaAdvance;
        this.blokirFleet = blokirFleet;
        this.approvalTreasuryStatus = approvalTreasuryStatus;
        this.remarks = remarks;
    }

    // Getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBiayaAdvance() {
        return biayaAdvance;
    }

    public void setBiayaAdvance(String biayaAdvance) {
        this.biayaAdvance = biayaAdvance;
    }

    public String getBlokirFleet() {
        return blokirFleet;
    }

    public void setBlokirFleet(String blokirFleet) {
        this.blokirFleet = blokirFleet;
    }

    public String getApprovalTreasuryStatus() {
        return approvalTreasuryStatus;
    }

    public void setApprovalTreasuryStatus(String approvalTreasuryStatus) {
        this.approvalTreasuryStatus = approvalTreasuryStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
