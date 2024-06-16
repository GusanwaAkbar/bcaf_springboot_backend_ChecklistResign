package com.hrs.checklist_resign.dto;

public class ApprovalSecurityAdministratorDTO {
    private long id;
    private String permohonanPenutupanUser;
    private String penutupanEmailBCA;
    private String pengembalianToken;
    private String approvalSecurityAdministratorStatus;
    private String remarks;

    public ApprovalSecurityAdministratorDTO() {
    }

    public ApprovalSecurityAdministratorDTO(long id, String permohonanPenutupanUser, String penutupanEmailBCA,
                                            String pengembalianToken, String approvalSecurityAdministratorStatus,
                                            String remarks) {
        this.id = id;
        this.permohonanPenutupanUser = permohonanPenutupanUser;
        this.penutupanEmailBCA = penutupanEmailBCA;
        this.pengembalianToken = pengembalianToken;
        this.approvalSecurityAdministratorStatus = approvalSecurityAdministratorStatus;
        this.remarks = remarks;
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
}
