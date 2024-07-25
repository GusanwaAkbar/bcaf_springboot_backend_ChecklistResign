package com.hrs.checklist_resign.dto;

import java.util.Date;
import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;


public class ResignationProgressDetailDTO {
    private Long id;
    private String nipUser;
    private String namaKaryawan;
    private Date tanggalBerakhirBekerja;
    private String approvalStatusAtasan;
    private String approvalGeneralServicesStatus;
    private String approvalHRIRStatus;
    private String approvalHRLearningStatus;
    private String approvalHRPayrollStatus;
    private String approvalHRServicesAdminStatus;
    private String approvalHRTalentStatus;
    private String approvalSecurityAdministratorStatus;
    private String approvalTreasuryStatus;
    private String finalApprovalStatus;
    private String nipAtasan;
    private String namaAtasan;
    private String emailAtasan;
    private Date createdDate;
    private Date approvedDate;
    private Date approvedDateAllDepartement;
    private Date approvedDateFinal;
    private ApprovalAtasanDTO approvalAtasanDTO;
    private ApprovalGeneralServicesDTO approvalGeneralServicesDTO;
    private ApprovalHRIRDTO approvalHRIRDTO;
    private ApprovalHRPayrollDTO approvalHRPayrollDTO;
    private ApprovalHRServicesAdminDTO approvalHRServicesAdminDTO;
    private ApprovalHRTalentDTO approvalHRTalentDTO;
    private ApprovalSecurityAdministratorDTO approvalSecurityAdministratorDTO;
    private ApprovalTreasuryDTO approvalTreasuryDTO;


    public ResignationProgressDetailDTO(
            Long id, String nipUser, String namaKaryawan, Date tanggalBerakhirBekerja,
            String approvalStatusAtasan, String approvalGeneralServicesStatus, String approvalHRIRStatus,
            String approvalHRLearningStatus, String approvalHRPayrollStatus, String approvalHRServicesAdminStatus,
            String approvalHRTalentStatus, String approvalSecurityAdministratorStatus, String approvalTreasuryStatus,
            String finalApprovalStatus, String nipAtasan, String namaAtasan, String emailAtasan,
            Date createdDate, Date approvedDate, Date approvedDateAllDepartement, Date approvedDateFinal,
            ApprovalAtasanDTO approvalAtasanDTO) {

        this.id = id;
        this.nipUser = nipUser;
        this.namaKaryawan = namaKaryawan;
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
        this.approvalStatusAtasan = approvalStatusAtasan;
        this.approvalGeneralServicesStatus = approvalGeneralServicesStatus;
        this.approvalHRIRStatus = approvalHRIRStatus;
        this.approvalHRLearningStatus = approvalHRLearningStatus;
        this.approvalHRPayrollStatus = approvalHRPayrollStatus;
        this.approvalHRServicesAdminStatus = approvalHRServicesAdminStatus;
        this.approvalHRTalentStatus = approvalHRTalentStatus;
        this.approvalSecurityAdministratorStatus = approvalSecurityAdministratorStatus;
        this.approvalTreasuryStatus = approvalTreasuryStatus;
        this.finalApprovalStatus = finalApprovalStatus;
        this.nipAtasan = nipAtasan;
        this.namaAtasan = namaAtasan;
        this.emailAtasan = emailAtasan;
        this.createdDate = createdDate;
        this.approvedDate = approvedDate;
        this.approvedDateAllDepartement = approvedDateAllDepartement;
        this.approvedDateFinal = approvedDateFinal;
        this.approvalAtasanDTO = approvalAtasanDTO;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNipUser() {
        return nipUser;
    }

    public void setNipUser(String nipUser) {
        this.nipUser = nipUser;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public Date getTanggalBerakhirBekerja() {
        return tanggalBerakhirBekerja;
    }

    public void setTanggalBerakhirBekerja(Date tanggalBerakhirBekerja) {
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
    }

    public String getApprovalAtasanStatus() {
        return approvalStatusAtasan;
    }

    public void setApprovalAtasanStatus(String approvalAtasanStatus) {
        this.approvalStatusAtasan = approvalAtasanStatus;
    }

    public String getApprovalGeneralServicesStatus() {
        return approvalGeneralServicesStatus;
    }

    public void setApprovalGeneralServicesStatus(String approvalGeneralServicesStatus) {
        this.approvalGeneralServicesStatus = approvalGeneralServicesStatus;
    }

    public String getApprovalHRIRStatus() {
        return approvalHRIRStatus;
    }

    public void setApprovalHRIRStatus(String approvalHRIRStatus) {
        this.approvalHRIRStatus = approvalHRIRStatus;
    }

    public String getApprovalHRLearningStatus() {
        return approvalHRLearningStatus;
    }

    public void setApprovalHRLearningStatus(String approvalHRLearningStatus) {
        this.approvalHRLearningStatus = approvalHRLearningStatus;
    }

    public String getApprovalHRPayrollStatus() {
        return approvalHRPayrollStatus;
    }

    public void setApprovalHRPayrollStatus(String approvalHRPayrollStatus) {
        this.approvalHRPayrollStatus = approvalHRPayrollStatus;
    }

    public String getApprovalHRServicesAdminStatus() {
        return approvalHRServicesAdminStatus;
    }

    public void setApprovalHRServicesAdminStatus(String approvalHRServicesAdminStatus) {
        this.approvalHRServicesAdminStatus = approvalHRServicesAdminStatus;
    }

    public String getApprovalHRTalentStatus() {
        return approvalHRTalentStatus;
    }

    public void setApprovalHRTalentStatus(String approvalHRTalentStatus) {
        this.approvalHRTalentStatus = approvalHRTalentStatus;
    }

    public String getApprovalSecurityAdministratorStatus() {
        return approvalSecurityAdministratorStatus;
    }

    public void setApprovalSecurityAdministratorStatus(String approvalSecurityAdministratorStatus) {
        this.approvalSecurityAdministratorStatus = approvalSecurityAdministratorStatus;
    }

    public String getApprovalTreasuryStatus() {
        return approvalTreasuryStatus;
    }

    public void setApprovalTreasuryStatus(String approvalTreasuryStatus) {
        this.approvalTreasuryStatus = approvalTreasuryStatus;
    }

    public String getFinalApprovalStatus() {
        return finalApprovalStatus;
    }

    public void setFinalApprovalStatus(String finalApprovalStatus) {
        this.finalApprovalStatus = finalApprovalStatus;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }

    public String getNamaAtasan() {
        return namaAtasan;
    }

    public void setNamaAtasan(String namaAtasan) {
        this.namaAtasan = namaAtasan;
    }

    public String getEmailAtasan() {
        return emailAtasan;
    }

    public void setEmailAtasan(String emailAtasan) {
        this.emailAtasan = emailAtasan;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getApprovedDateAllDepartement() {
        return approvedDateAllDepartement;
    }

    public void setApprovedDateAllDepartement(Date approvedDateAllDepartement) {
        this.approvedDateAllDepartement = approvedDateAllDepartement;
    }

    public Date getApprovedDateFinal() {
        return approvedDateFinal;
    }

    public void setApprovedDateFinal(Date approvedDateFinal) {
        this.approvedDateFinal = approvedDateFinal;
    }

    public ApprovalAtasanDTO getApprovalAtasanDTO() {
        return approvalAtasanDTO;
    }

    public void setApprovalAtasanDTO(ApprovalAtasanDTO approvalAtasanDTO) {
        this.approvalAtasanDTO = approvalAtasanDTO;
    }

    public ApprovalGeneralServicesDTO getApprovalGeneralServicesDTO() {
        return approvalGeneralServicesDTO;
    }

    public void setApprovalGeneralServicesDTO(ApprovalGeneralServicesDTO approvalGeneralServicesDTO) {
        this.approvalGeneralServicesDTO = approvalGeneralServicesDTO;
    }

    public ApprovalHRIRDTO getApprovalHRIRDTO() {
        return approvalHRIRDTO;
    }

    public void setApprovalHRIRDTO(ApprovalHRIRDTO approvalHRIRDTO) {
        this.approvalHRIRDTO = approvalHRIRDTO;
    }

    public ApprovalHRPayrollDTO getApprovalHRPayrollDTO() {
        return approvalHRPayrollDTO;
    }

    public void setApprovalHRPayrollDTO(ApprovalHRPayrollDTO approvalHRPayrollDTO) {
        this.approvalHRPayrollDTO = approvalHRPayrollDTO;
    }

    public ApprovalHRServicesAdminDTO getApprovalHRServicesAdminDTO() {
        return approvalHRServicesAdminDTO;
    }

    public void setApprovalHRServicesAdminDTO(ApprovalHRServicesAdminDTO approvalHRServicesAdminDTO) {
        this.approvalHRServicesAdminDTO = approvalHRServicesAdminDTO;
    }

    public ApprovalHRTalentDTO getApprovalHRTalentDTO() {
        return approvalHRTalentDTO;
    }

    public void setApprovalHRTalentDTO(ApprovalHRTalentDTO approvalHRTalentDTO) {
        this.approvalHRTalentDTO = approvalHRTalentDTO;
    }

    public ApprovalSecurityAdministratorDTO getApprovalSecurityAdministratorDTO() {
        return approvalSecurityAdministratorDTO;
    }

    public void setApprovalSecurityAdministratorDTO(ApprovalSecurityAdministratorDTO approvalSecurityAdministratorDTO) {
        this.approvalSecurityAdministratorDTO = approvalSecurityAdministratorDTO;
    }

    public ApprovalTreasuryDTO getApprovalTreasuryDTO() {
        return approvalTreasuryDTO;
    }

    public void setApprovalTreasuryDTO(ApprovalTreasuryDTO approvalTreasuryDTO) {
        this.approvalTreasuryDTO = approvalTreasuryDTO;
    }
}