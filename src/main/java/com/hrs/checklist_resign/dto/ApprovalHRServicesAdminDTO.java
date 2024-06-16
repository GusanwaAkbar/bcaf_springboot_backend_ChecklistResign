package com.hrs.checklist_resign.dto;

public class ApprovalHRServicesAdminDTO {
    private long id;
    private String excessOfClaim;
    private String penyelesaianBiayaHR;
    private String penonaktifanKartuElektronik;
    private String approvalHRServicesAdminStatus;
    private String remarks;

    public ApprovalHRServicesAdminDTO() {
    }

    public ApprovalHRServicesAdminDTO(long id, String excessOfClaim, String penyelesaianBiayaHR,
                                      String penonaktifanKartuElektronik, String approvalHRServicesAdminStatus,
                                      String remarks) {
        this.id = id;
        this.excessOfClaim = excessOfClaim;
        this.penyelesaianBiayaHR = penyelesaianBiayaHR;
        this.penonaktifanKartuElektronik = penonaktifanKartuElektronik;
        this.approvalHRServicesAdminStatus = approvalHRServicesAdminStatus;
        this.remarks = remarks;
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
}
