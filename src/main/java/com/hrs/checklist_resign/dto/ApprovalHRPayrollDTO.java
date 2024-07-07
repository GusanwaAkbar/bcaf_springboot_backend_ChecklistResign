package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalHRPayrollDTO {
    private Long id;
    private String softLoan;
    private String emergencyLoan;
    private String smartphoneLoan;
    private String motorLoan;
    private String umkLoan;
    private String laptopLoan;
    private String approvalHRPayrollStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalHRPayrollDTO() {}

    public ApprovalHRPayrollDTO(Long id, String softLoan, String emergencyLoan, String smartphoneLoan,
                                String motorLoan, String umkLoan, String laptopLoan,
                                String approvalHRPayrollStatus, String remarks, Date approvedDate, Date createdDate,
                                String approvedBy, String documentPath) {
        this.id = id;
        this.softLoan = softLoan;
        this.emergencyLoan = emergencyLoan;
        this.smartphoneLoan = smartphoneLoan;
        this.motorLoan = motorLoan;
        this.umkLoan = umkLoan;
        this.laptopLoan = laptopLoan;
        this.approvalHRPayrollStatus = approvalHRPayrollStatus;
        this.remarks = remarks;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.documentPath = documentPath;
    }

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftLoan() {
        return softLoan;
    }

    public void setSoftLoan(String softLoan) {
        this.softLoan = softLoan;
    }

    public String getEmergencyLoan() {
        return emergencyLoan;
    }

    public void setEmergencyLoan(String emergencyLoan) {
        this.emergencyLoan = emergencyLoan;
    }

    public String getSmartphoneLoan() {
        return smartphoneLoan;
    }

    public void setSmartphoneLoan(String smartphoneLoan) {
        this.smartphoneLoan = smartphoneLoan;
    }

    public String getMotorLoan() {
        return motorLoan;
    }

    public void setMotorLoan(String motorLoan) {
        this.motorLoan = motorLoan;
    }

    public String getUmkLoan() {
        return umkLoan;
    }

    public void setUmkLoan(String umkLoan) {
        this.umkLoan = umkLoan;
    }

    public String getLaptopLoan() {
        return laptopLoan;
    }

    public void setLaptopLoan(String laptopLoan) {
        this.laptopLoan = laptopLoan;
    }

    public String getApprovalHRPayrollStatus() {
        return approvalHRPayrollStatus;
    }

    public void setApprovalHRPayrollStatus(String approvalHRPayrollStatus) {
        this.approvalHRPayrollStatus = approvalHRPayrollStatus;
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
