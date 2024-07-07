package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalHRServicesAdminDTO {
    private long id;
    private String excessOfClaim;
    private String penyelesaianBiayaHR;
    private String penonaktifanKartuElektronik;
    private String approvalHRServicesAdminStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalHRServicesAdminDTO() {}

    public ApprovalHRServicesAdminDTO(long id, String excessOfClaim, String penyelesaianBiayaHR,
                                      String penonaktifanKartuElektronik, String approvalHRServicesAdminStatus,
                                      String remarks, Date approvedDate, Date createdDate, String approvedBy,
                                      String documentPath) {
        this.id = id;
        this.excessOfClaim = excessOfClaim;
        this.penyelesaianBiayaHR = penyelesaianBiayaHR;
        this.penonaktifanKartuElektronik = penonaktifanKartuElektronik;
        this.approvalHRServicesAdminStatus = approvalHRServicesAdminStatus;
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

    public String getExcessOfClaim() {
        return excessOfClaim;
    }

    public void setExcessOfClaim(String excessOfClaim) {
        this.excessOfClaim = excessOfClaim;
    }

    public String getPenyelesaianBiayaHR() {
        return penyelesaianBiayaHR;
    }

    public void setPenyelesaianBiayaHR(String penyelesaianBiayaHR) {
        this.penyelesaianBiayaHR = penyelesaianBiayaHR;
    }

    public String getPenonaktifanKartuElektronik() {
        return penonaktifanKartuElektronik;
    }

    public void setPenonaktifanKartuElektronik(String penonaktifanKartuElektronik) {
        this.penonaktifanKartuElektronik = penonaktifanKartuElektronik;
    }

    public String getApprovalHRServicesAdminStatus() {
        return approvalHRServicesAdminStatus;
    }

    public void setApprovalHRServicesAdminStatus(String approvalHRServicesAdminStatus) {
        this.approvalHRServicesAdminStatus = approvalHRServicesAdminStatus;
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
