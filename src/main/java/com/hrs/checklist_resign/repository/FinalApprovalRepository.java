package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.dto.FinalApprovalDTO;
import com.hrs.checklist_resign.Model.FinalApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinalApprovalRepository extends JpaRepository<FinalApproval, Long> {

    @Query("SELECT new com.hrs.checklist_resign.dto.FinalApprovalDTO(fa.id, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(uda.id, uda.nama, uda.email, uda.cabang, uda.idDivisi, uda.divisi, uda.jabatan, uda.externalUser, uda.user.username), " +
            "new com.hrs.checklist_resign.dto.ApprovalGeneralServicesDTO(ags.id, ags.penutupanPin, ags.pengembalianKendaraanDinas, ags.inventarisKantor, ags.pengembalianAktiva, ags.pengembalianKendaraanUMK3, ags.approvalGeneralServicesStatus, ags.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRIRDTO(hrir.id, hrir.exitInterview, hrir.approvalHRIRStatus, hrir.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRLearningDTO(hrl.id, hrl.pengecekanBiayaTraining, hrl.approvalHRLearningStatus, hrl.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRPayrollDTO(hrp.id, hrp.softLoan, hrp.emergencyLoan, hrp.smartphoneLoan, hrp.motorLoan, hrp.umkLoan, hrp.laptopLoan, hrp.approvalHRPayrollStatus, hrp.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRServicesAdminDTO(hrsa.id, hrsa.excessOfClaim, hrsa.penyelesaianBiayaHR, hrsa.penonaktifanKartuElektronik, hrsa.approvalHRServicesAdminStatus, hrsa.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRTalentDTO(hrtalent.id, hrtalent.pengecekanBiaya, hrtalent.approvalHRTalentStatus, hrtalent.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalSecurityAdministratorDTO(securityAdmin.id, securityAdmin.permohonanPenutupanUser, securityAdmin.penutupanEmailBCA, securityAdmin.pengembalianToken, securityAdmin.approvalSecurityAdministratorStatus, securityAdmin.remarks), " +
            "new com.hrs.checklist_resign.dto.ApprovalTreasuryDTO(treasury.id, treasury.biayaAdvance, treasury.blokirFleet, treasury.approvalTreasuryStatus, treasury.remarks), " +
            "fa.finalApprovalStatus, fa.remarks) " +
            "FROM FinalApproval fa " +
            "JOIN fa.userDetailResign udr " +
            "JOIN fa.userDetailAtasan uda " +
            "LEFT JOIN fa.approvalGeneralServices ags " +
            "LEFT JOIN fa.approvalHRIR hrir " +
            "LEFT JOIN fa.approvalHRLearning hrl " +
            "LEFT JOIN fa.approvalHRPayroll hrp " +
            "LEFT JOIN fa.approvalHRServicesAdmin hrsa " +
            "LEFT JOIN fa.approvalHRTalent hrtalent " +
            "LEFT JOIN fa.approvalSecurityAdministrator securityAdmin " +
            "LEFT JOIN fa.approvalTreasury treasury " +
            "WHERE fa.id = :id")
    Optional<FinalApprovalDTO> findFinalApprovalDTOById(@Param("id") Long id);


}
