package com.hrs.checklist_resign.dto;

public class FinalApprovalDTO {
    private Long id;
    private UserDetailDTO userDetailResign;
    private UserDetailDTO userDetailAtasan;
    private PengajuanResignDTO pengajuanResign;  // Add this field

    private ApprovalAtasanDTO approvalAtasan;
    private ApprovalGeneralServicesDTO approvalGeneralServices;
    private ApprovalHRIRDTO approvalHRIR;
    private ApprovalHRLearningDTO approvalHRLearning;
    private ApprovalHRPayrollDTO approvalHRPayroll;
    private ApprovalHRServicesAdminDTO approvalHRServicesAdmin;
    private ApprovalHRTalentDTO approvalHRTalent;
    private ApprovalSecurityAdministratorDTO approvalSecurityAdministrator;
    private ApprovalTreasuryDTO approvalTreasury;
    private String finalApprovalStatus;
    private String remarks;

    // Constructor with all fields
    public FinalApprovalDTO(Long id, UserDetailDTO userDetailResign, UserDetailDTO userDetailAtasan,
                            PengajuanResignDTO pengajuanResign, ApprovalAtasanDTO approvalAtasan,  // Add this parameter
                            ApprovalGeneralServicesDTO approvalGeneralServices, ApprovalHRIRDTO approvalHRIR,
                            ApprovalHRLearningDTO approvalHRLearning, ApprovalHRPayrollDTO approvalHRPayroll,
                            ApprovalHRServicesAdminDTO approvalHRServicesAdmin, ApprovalHRTalentDTO approvalHRTalent,
                            ApprovalSecurityAdministratorDTO approvalSecurityAdministrator, ApprovalTreasuryDTO approvalTreasury,
                            String finalApprovalStatus, String remarks) {
        this.id = id;
        this.userDetailResign = userDetailResign;
        this.userDetailAtasan = userDetailAtasan;
        this.pengajuanResign = pengajuanResign;  // Initialize this field
        this.approvalAtasan = approvalAtasan;
        this.approvalGeneralServices = approvalGeneralServices;
        this.approvalHRIR = approvalHRIR;
        this.approvalHRLearning = approvalHRLearning;
        this.approvalHRPayroll = approvalHRPayroll;
        this.approvalHRServicesAdmin = approvalHRServicesAdmin;
        this.approvalHRTalent = approvalHRTalent;
        this.approvalSecurityAdministrator = approvalSecurityAdministrator;
        this.approvalTreasury = approvalTreasury;
        this.finalApprovalStatus = finalApprovalStatus;
        this.remarks = remarks;
    }

    // No-arg constructor
    public FinalApprovalDTO() {
    }

    // set and get


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApprovalAtasanDTO getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasanDTO approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public UserDetailDTO getUserDetailResign() {
        return userDetailResign;
    }

    public void setUserDetailResign(UserDetailDTO userDetailResign) {
        this.userDetailResign = userDetailResign;
    }

    public UserDetailDTO getUserDetailAtasan() {
        return userDetailAtasan;
    }

    public void setUserDetailAtasan(UserDetailDTO userDetailAtasan) {
        this.userDetailAtasan = userDetailAtasan;
    }

    public PengajuanResignDTO getPengajuanResign() {
        return pengajuanResign;
    }

    public void setPengajuanResign(PengajuanResignDTO pengajuanResign) {
        this.pengajuanResign = pengajuanResign;
    }

    public ApprovalGeneralServicesDTO getApprovalGeneralServices() {
        return approvalGeneralServices;
    }

    public void setApprovalGeneralServices(ApprovalGeneralServicesDTO approvalGeneralServices) {
        this.approvalGeneralServices = approvalGeneralServices;
    }

    public ApprovalHRIRDTO getApprovalHRIR() {
        return approvalHRIR;
    }

    public void setApprovalHRIR(ApprovalHRIRDTO approvalHRIR) {
        this.approvalHRIR = approvalHRIR;
    }

    public ApprovalHRLearningDTO getApprovalHRLearning() {
        return approvalHRLearning;
    }

    public void setApprovalHRLearning(ApprovalHRLearningDTO approvalHRLearning) {
        this.approvalHRLearning = approvalHRLearning;
    }

    public ApprovalHRPayrollDTO getApprovalHRPayroll() {
        return approvalHRPayroll;
    }

    public void setApprovalHRPayroll(ApprovalHRPayrollDTO approvalHRPayroll) {
        this.approvalHRPayroll = approvalHRPayroll;
    }

    public ApprovalHRServicesAdminDTO getApprovalHRServicesAdmin() {
        return approvalHRServicesAdmin;
    }

    public void setApprovalHRServicesAdmin(ApprovalHRServicesAdminDTO approvalHRServicesAdmin) {
        this.approvalHRServicesAdmin = approvalHRServicesAdmin;
    }

    public ApprovalHRTalentDTO getApprovalHRTalent() {
        return approvalHRTalent;
    }

    public void setApprovalHRTalent(ApprovalHRTalentDTO approvalHRTalent) {
        this.approvalHRTalent = approvalHRTalent;
    }

    public ApprovalSecurityAdministratorDTO getApprovalSecurityAdministrator() {
        return approvalSecurityAdministrator;
    }

    public void setApprovalSecurityAdministrator(ApprovalSecurityAdministratorDTO approvalSecurityAdministrator) {
        this.approvalSecurityAdministrator = approvalSecurityAdministrator;
    }

    public ApprovalTreasuryDTO getApprovalTreasury() {
        return approvalTreasury;
    }

    public void setApprovalTreasury(ApprovalTreasuryDTO approvalTreasury) {
        this.approvalTreasury = approvalTreasury;
    }

    public String getFinalApprovalStatus() {
        return finalApprovalStatus;
    }

    public void setFinalApprovalStatus(String finalApprovalStatus) {
        this.finalApprovalStatus = finalApprovalStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
