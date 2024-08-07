package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalTreasuryDTO {
    private long id;
    private String biayaAdvance;
    private String blokirFleet;
    private String approvalTreasuryStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalTreasuryDTO() {}

    public ApprovalTreasuryDTO(long id, String biayaAdvance, String blokirFleet, String approvalTreasuryStatus,
                               String remarks, Date approvedDate, Date createdDate, String approvedBy,
                               String documentPath) {
        this.id = id;
        this.biayaAdvance = biayaAdvance;
        this.blokirFleet = blokirFleet;
        this.approvalTreasuryStatus = approvalTreasuryStatus;
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
