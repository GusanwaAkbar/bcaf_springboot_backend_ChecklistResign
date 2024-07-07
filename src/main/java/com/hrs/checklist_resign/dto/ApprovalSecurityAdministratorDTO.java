package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalSecurityAdministratorDTO {
    private long id;
    private String permohonanPenutupanUser;
    private String penutupanEmailBCA;
    private String pengembalianToken;
    private String approvalSecurityAdministratorStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalSecurityAdministratorDTO() {}

    public ApprovalSecurityAdministratorDTO(long id, String permohonanPenutupanUser, String penutupanEmailBCA,
                                            String pengembalianToken, String approvalSecurityAdministratorStatus,
                                            String remarks, Date approvedDate, Date createdDate, String approvedBy,
                                            String documentPath) {
        this.id = id;
        this.permohonanPenutupanUser = permohonanPenutupanUser;
        this.penutupanEmailBCA = penutupanEmailBCA;
        this.pengembalianToken = pengembalianToken;
        this.approvalSecurityAdministratorStatus = approvalSecurityAdministratorStatus;
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

    public String getPermohonanPenutupanUser() {
        return permohonanPenutupanUser;
    }

    public void setPermohonanPenutupanUser(String permohonanPenutupanUser) {
        this.permohonanPenutupanUser = permohonanPenutupanUser;
    }

    public String getPenutupanEmailBCA() {
        return penutupanEmailBCA;
    }

    public void setPenutupanEmailBCA(String penutupanEmailBCA) {
        this.penutupanEmailBCA = penutupanEmailBCA;
    }

    public String getPengembalianToken() {
        return pengembalianToken;
    }

    public void setPengembalianToken(String pengembalianToken) {
        this.pengembalianToken = pengembalianToken;
    }

    public String getApprovalSecurityAdministratorStatus() {
        return approvalSecurityAdministratorStatus;
    }

    public void setApprovalSecurityAdministratorStatus(String approvalSecurityAdministratorStatus) {
        this.approvalSecurityAdministratorStatus = approvalSecurityAdministratorStatus;
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
