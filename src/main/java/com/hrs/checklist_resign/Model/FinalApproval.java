package com.hrs.checklist_resign.Model;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "app_hrs_resign_final_approval")
public class FinalApproval implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nip_karyawan_resign")
    private String nipKaryawanResign;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_detail_resign_id", referencedColumnName = "id")
    private UserDetail userDetailResign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_detail_atasan_id", referencedColumnName = "id")
    private UserDetail userDetailAtasan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pengajuan_resign_id", referencedColumnName = "id")
    private PengajuanResign pengajuanResign;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    private ApprovalAtasan approvalAtasan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_treasury_id")
    private ApprovalTreasury approvalTreasury;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_general_services_id")
    private ApprovalGeneralServices approvalGeneralServices;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_hr_ir_id")
    private ApprovalHRIR approvalHRIR;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_hr_learning_id")
    private ApprovalHRLearning approvalHRLearning;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_hr_payroll_id")
    private ApprovalHRPayroll approvalHRPayroll;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_hr_services_admin_id")
    private ApprovalHRServicesAdmin approvalHRServicesAdmin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_hr_talent_id")
    private ApprovalHRTalent approvalHRTalent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_security_administrator_id")
    private ApprovalSecurityAdministrator approvalSecurityAdministrator;

    private String finalApprovalStatus;
    private String remarks;


    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNipKaryawanResign() {
        return nipKaryawanResign;
    }

    public void setNipKaryawanResign(String nipKaryawanResign) {
        this.nipKaryawanResign = nipKaryawanResign;
    }

    public PengajuanResign getPengajuanResign() {
        return pengajuanResign;
    }

    public void setPengajuanResign(PengajuanResign pengajuanResign) {
        this.pengajuanResign = pengajuanResign;
    }

    public ApprovalAtasan getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasan approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public ApprovalTreasury getApprovalTreasury() {
        return approvalTreasury;
    }

    public void setApprovalTreasury(ApprovalTreasury approvalTreasury) {
        this.approvalTreasury = approvalTreasury;
    }

    public ApprovalGeneralServices getApprovalGeneralServices() {
        return approvalGeneralServices;
    }

    public void setApprovalGeneralServices(ApprovalGeneralServices approvalGeneralServices) {
        this.approvalGeneralServices = approvalGeneralServices;
    }

    public ApprovalHRIR getApprovalHRIR() {
        return approvalHRIR;
    }

    public void setApprovalHRIR(ApprovalHRIR approvalHRIR) {
        this.approvalHRIR = approvalHRIR;
    }

    public ApprovalHRLearning getApprovalHRLearning() {
        return approvalHRLearning;
    }

    public void setApprovalHRLearning(ApprovalHRLearning approvalHRLearning) {
        this.approvalHRLearning = approvalHRLearning;
    }

    public ApprovalHRPayroll getApprovalHRPayroll() {
        return approvalHRPayroll;
    }

    public void setApprovalHRPayroll(ApprovalHRPayroll approvalHRPayroll) {
        this.approvalHRPayroll = approvalHRPayroll;
    }

    public ApprovalHRServicesAdmin getApprovalHRServicesAdmin() {
        return approvalHRServicesAdmin;
    }

    public void setApprovalHRServicesAdmin(ApprovalHRServicesAdmin approvalHRServicesAdmin) {
        this.approvalHRServicesAdmin = approvalHRServicesAdmin;
    }

    public ApprovalHRTalent getApprovalHRTalent() {
        return approvalHRTalent;
    }

    public void setApprovalHRTalent(ApprovalHRTalent approvalHRTalent) {
        this.approvalHRTalent = approvalHRTalent;
    }

    public ApprovalSecurityAdministrator getApprovalSecurityAdministrator() {
        return approvalSecurityAdministrator;
    }

    public void setApprovalSecurityAdministrator(ApprovalSecurityAdministrator approvalSecurityAdministrator) {
        this.approvalSecurityAdministrator = approvalSecurityAdministrator;
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

    public UserDetail getUserDetailResign() {
        return userDetailResign;
    }

    public void setUserDetailResign(UserDetail userDetailResign) {
        this.userDetailResign = userDetailResign;
    }

    public UserDetail getUserDetailAtasan() {
        return userDetailAtasan;
    }

    public void setUserDetailAtasan(UserDetail userDetailAtasan) {
        this.userDetailAtasan = userDetailAtasan;
    }
}

