package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalSecurityAdministrator;
import com.hrs.checklist_resign.dto.FinalApprovalDTO;
import com.hrs.checklist_resign.Model.FinalApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinalApprovalRepository extends JpaRepository<FinalApproval, Long> {

    Optional<FinalApproval> findByNipKaryawanResign(String nipKaryawanResign);



    @Query("SELECT new com.hrs.checklist_resign.dto.FinalApprovalDTO(fa.id, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(uda.id, uda.nama, uda.email, uda.cabang, uda.idDivisi, uda.divisi, uda.jabatan, uda.externalUser, uda.user.username), " +
            "new com.hrs.checklist_resign.dto.PengajuanResignDTO(pr.id, pr.isiUntukOrangLain, pr.tanggalPembuatanAkunHRIS, pr.tanggalBerakhirBekerja, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), pr.nipAtasan, pr.emailAtasan, pr.approvedDate, pr.createdDate, pr.approvedBy, pr.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalAtasanDTO(aa.id, aa.nipAtasan, aa.emailAtasan, aa.serahTerimaTugas, aa.pengembalianNotebook, aa.pengembalianKunciLoker, aa.pengembalianKunciRuangan, aa.penyerahanSuratPengunduranDiri, aa.pengembalianIdCard, aa.hapusAplikasiMobile, aa.uninstallSoftwareNotebook, aa.uninstallSoftwareUnitKerja, aa.approvalStatusAtasan, aa.remarksAtasan, aa.approvedDate, aa.createdDate, aa.approvedBy, aa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalGeneralServicesDTO(ags.id, ags.penutupanPin, ags.pengembalianKendaraanDinas, ags.inventarisKantor, ags.pengembalianAktiva, ags.pengembalianKendaraanUMK3, ags.approvalGeneralServicesStatus, ags.remarks, ags.approvedDate, ags.createdDate, ags.approvedBy, ags.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRIRDTO(hrir.id, hrir.exitInterview, hrir.approvalHRIRStatus, hrir.remarks, hrir.approvedDate, hrir.createdDate, hrir.approvedBy, hrir.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRLearningDTO(hrl.id, hrl.pengecekanBiayaTraining, hrl.approvalHRLearningStatus, hrl.remarks, hrl.approvedDate, hrl.createdDate, hrl.approvedBy, hrl.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRPayrollDTO(hrp.id, hrp.softLoan, hrp.emergencyLoan, hrp.smartphoneLoan, hrp.motorLoan, hrp.umkLoan, hrp.laptopLoan, hrp.approvalHRPayrollStatus, hrp.remarks, hrp.approvedDate, hrp.createdDate, hrp.approvedBy, hrp.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRServicesAdminDTO(hrsa.id, hrsa.excessOfClaim, hrsa.penyelesaianBiayaHR, hrsa.penonaktifanKartuElektronik, hrsa.approvalHRServicesAdminStatus, hrsa.remarks, hrsa.approvedDate, hrsa.createdDate, hrsa.approvedBy, hrsa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRTalentDTO(hrt.id, hrt.pengecekanBiaya, hrt.approvalHRTalentStatus, hrt.remarks, hrt.approvedDate, hrt.createdDate, hrt.approvedBy, hrt.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalSecurityAdministratorDTO(sa.id, sa.permohonanPenutupanUser, sa.penutupanEmailBCA, sa.pengembalianToken, sa.approvalSecurityAdministratorStatus, sa.remarks, sa.approvedDate, sa.createdDate, sa.approvedBy, sa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalTreasuryDTO(at.id, at.biayaAdvance, at.blokirFleet, at.approvalTreasuryStatus, at.remarks, at.approvedDate, at.createdDate, at.approvedBy, at.documentPath), " +
            "fa.finalApprovalStatus, fa.remarks, fa.approvedDate, fa.createdDate, fa.approvedBy, fa.documentPath) " +
            "FROM FinalApproval fa " +
            "JOIN fa.userDetailResign udr " +
            "JOIN fa.userDetailAtasan uda " +
            "JOIN fa.pengajuanResign pr " +
            "JOIN fa.approvalAtasan aa " +
            "JOIN fa.approvalGeneralServices ags " +
            "JOIN fa.approvalHRIR hrir " +
            "JOIN fa.approvalHRLearning hrl " +
            "JOIN fa.approvalHRPayroll hrp " +
            "JOIN fa.approvalHRServicesAdmin hrsa " +
            "JOIN fa.approvalHRTalent hrt " +
            "JOIN fa.approvalSecurityAdministrator sa " +
            "JOIN fa.approvalTreasury at " +
            "WHERE fa.id = :id")
    Optional<FinalApprovalDTO> findFinalApprovalDTOById(@Param("id") Long id);





    @Query("SELECT new com.hrs.checklist_resign.dto.FinalApprovalDTO(fa.id, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(uda.id, uda.nama, uda.email, uda.cabang, uda.idDivisi, uda.divisi, uda.jabatan, uda.externalUser, uda.user.username), " +
            "new com.hrs.checklist_resign.dto.PengajuanResignDTO(pr.id, pr.isiUntukOrangLain, pr.tanggalPembuatanAkunHRIS, pr.tanggalBerakhirBekerja, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), pr.nipAtasan, pr.emailAtasan, pr.approvedDate, pr.createdDate, pr.approvedBy, pr.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalAtasanDTO(aa.id, aa.nipAtasan, aa.emailAtasan, aa.serahTerimaTugas, aa.pengembalianNotebook, aa.pengembalianKunciLoker, aa.pengembalianKunciRuangan, aa.penyerahanSuratPengunduranDiri, aa.pengembalianIdCard, aa.hapusAplikasiMobile, aa.uninstallSoftwareNotebook, aa.uninstallSoftwareUnitKerja, aa.approvalStatusAtasan, aa.remarksAtasan, aa.approvedDate, aa.createdDate, aa.approvedBy, aa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalGeneralServicesDTO(ags.id, ags.penutupanPin, ags.pengembalianKendaraanDinas, ags.inventarisKantor, ags.pengembalianAktiva, ags.pengembalianKendaraanUMK3, ags.approvalGeneralServicesStatus, ags.remarks, ags.approvedDate, ags.createdDate, ags.approvedBy, ags.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRIRDTO(hrir.id, hrir.exitInterview, hrir.approvalHRIRStatus, hrir.remarks, hrir.approvedDate, hrir.createdDate, hrir.approvedBy, hrir.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRLearningDTO(hrl.id, hrl.pengecekanBiayaTraining, hrl.approvalHRLearningStatus, hrl.remarks, hrl.approvedDate, hrl.createdDate, hrl.approvedBy, hrl.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRPayrollDTO(hrp.id, hrp.softLoan, hrp.emergencyLoan, hrp.smartphoneLoan, hrp.motorLoan, hrp.umkLoan, hrp.laptopLoan, hrp.approvalHRPayrollStatus, hrp.remarks, hrp.approvedDate, hrp.createdDate, hrp.approvedBy, hrp.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRServicesAdminDTO(hrsa.id, hrsa.excessOfClaim, hrsa.penyelesaianBiayaHR, hrsa.penonaktifanKartuElektronik, hrsa.approvalHRServicesAdminStatus, hrsa.remarks, hrsa.approvedDate, hrsa.createdDate, hrsa.approvedBy, hrsa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRTalentDTO(hrt.id, hrt.pengecekanBiaya, hrt.approvalHRTalentStatus, hrt.remarks, hrt.approvedDate, hrt.createdDate, hrt.approvedBy, hrt.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalSecurityAdministratorDTO(sa.id, sa.permohonanPenutupanUser, sa.penutupanEmailBCA, sa.pengembalianToken, sa.approvalSecurityAdministratorStatus, sa.remarks, sa.approvedDate, sa.createdDate, sa.approvedBy, sa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalTreasuryDTO(at.id, at.biayaAdvance, at.blokirFleet, at.approvalTreasuryStatus, at.remarks, at.approvedDate, at.createdDate, at.approvedBy, at.documentPath), " +
            "fa.finalApprovalStatus, fa.remarks, fa.approvedDate, fa.createdDate, fa.approvedBy, fa.documentPath) " +
            "FROM FinalApproval fa " +
            "JOIN fa.userDetailResign udr " +
            "JOIN fa.userDetailAtasan uda " +
            "JOIN fa.pengajuanResign pr " +
            "JOIN fa.approvalAtasan aa " +
            "JOIN fa.approvalGeneralServices ags " +
            "JOIN fa.approvalHRIR hrir " +
            "JOIN fa.approvalHRLearning hrl " +
            "JOIN fa.approvalHRPayroll hrp " +
            "JOIN fa.approvalHRServicesAdmin hrsa " +
            "JOIN fa.approvalHRTalent hrt " +
            "JOIN fa.approvalSecurityAdministrator sa " +
            "JOIN fa.approvalTreasury at " )
    List<FinalApprovalDTO> findAllFinalApprovalDTOs();


    Page<FinalApproval> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndFinalApprovalStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String finalApprovalStatus,
            Pageable pageable
    );


    //===================-------========


    @Query("SELECT new com.hrs.checklist_resign.dto.FinalApprovalDTO(fa.id, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(uda.id, uda.nama, uda.email, uda.cabang, uda.idDivisi, uda.divisi, uda.jabatan, uda.externalUser, uda.user.username), " +
            "new com.hrs.checklist_resign.dto.PengajuanResignDTO(pr.id, pr.isiUntukOrangLain, pr.tanggalPembuatanAkunHRIS, pr.tanggalBerakhirBekerja, " +
            "new com.hrs.checklist_resign.dto.UserDetailDTO(udr.id, udr.nama, udr.email, udr.cabang, udr.idDivisi, udr.divisi, udr.jabatan, udr.externalUser, udr.user.username), pr.nipAtasan, pr.emailAtasan, pr.approvedDate, pr.createdDate, pr.approvedBy, pr.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalAtasanDTO(aa.id, aa.nipAtasan, aa.emailAtasan, aa.serahTerimaTugas, aa.pengembalianNotebook, aa.pengembalianKunciLoker, aa.pengembalianKunciRuangan, aa.penyerahanSuratPengunduranDiri, aa.pengembalianIdCard, aa.hapusAplikasiMobile, aa.uninstallSoftwareNotebook, aa.uninstallSoftwareUnitKerja, aa.approvalStatusAtasan, aa.remarksAtasan, aa.approvedDate, aa.createdDate, aa.approvedBy, aa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalGeneralServicesDTO(ags.id, ags.penutupanPin, ags.pengembalianKendaraanDinas, ags.inventarisKantor, ags.pengembalianAktiva, ags.pengembalianKendaraanUMK3, ags.approvalGeneralServicesStatus, ags.remarks, ags.approvedDate, ags.createdDate, ags.approvedBy, ags.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRIRDTO(hrir.id, hrir.exitInterview, hrir.approvalHRIRStatus, hrir.remarks, hrir.approvedDate, hrir.createdDate, hrir.approvedBy, hrir.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRLearningDTO(hrl.id, hrl.pengecekanBiayaTraining, hrl.approvalHRLearningStatus, hrl.remarks, hrl.approvedDate, hrl.createdDate, hrl.approvedBy, hrl.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRPayrollDTO(hrp.id, hrp.softLoan, hrp.emergencyLoan, hrp.smartphoneLoan, hrp.motorLoan, hrp.umkLoan, hrp.laptopLoan, hrp.approvalHRPayrollStatus, hrp.remarks, hrp.approvedDate, hrp.createdDate, hrp.approvedBy, hrp.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRServicesAdminDTO(hrsa.id, hrsa.excessOfClaim, hrsa.penyelesaianBiayaHR, hrsa.penonaktifanKartuElektronik, hrsa.approvalHRServicesAdminStatus, hrsa.remarks, hrsa.approvedDate, hrsa.createdDate, hrsa.approvedBy, hrsa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalHRTalentDTO(hrt.id, hrt.pengecekanBiaya, hrt.approvalHRTalentStatus, hrt.remarks, hrt.approvedDate, hrt.createdDate, hrt.approvedBy, hrt.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalSecurityAdministratorDTO(sa.id, sa.permohonanPenutupanUser, sa.penutupanEmailBCA, sa.pengembalianToken, sa.approvalSecurityAdministratorStatus, sa.remarks, sa.approvedDate, sa.createdDate, sa.approvedBy, sa.documentPath), " +
            "new com.hrs.checklist_resign.dto.ApprovalTreasuryDTO(at.id, at.biayaAdvance, at.blokirFleet, at.approvalTreasuryStatus, at.remarks, at.approvedDate, at.createdDate, at.approvedBy, at.documentPath), " +
            "fa.finalApprovalStatus, fa.remarks, fa.approvedDate, fa.createdDate, fa.approvedBy, fa.documentPath) " +
            "FROM FinalApproval fa " +
            "JOIN fa.userDetailResign udr " +
            "JOIN fa.userDetailAtasan uda " +
            "JOIN fa.pengajuanResign pr " +
            "JOIN fa.approvalAtasan aa " +
            "JOIN fa.approvalGeneralServices ags " +
            "JOIN fa.approvalHRIR hrir " +
            "JOIN fa.approvalHRLearning hrl " +
            "JOIN fa.approvalHRPayroll hrp " +
            "JOIN fa.approvalHRServicesAdmin hrsa " +
            "JOIN fa.approvalHRTalent hrt " +
            "JOIN fa.approvalSecurityAdministrator sa " +
            "JOIN fa.approvalTreasury at " +
            "WHERE (:nipKaryawanResign IS NULL OR LOWER(udr.user.username) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) " +
            "AND (:namaKaryawan IS NULL OR LOWER(udr.nama) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) " +
            "AND (:finalApprovalStatus = 'null' AND fa.finalApprovalStatus IS NULL " +
            "     OR :finalApprovalStatus != 'null' AND LOWER(fa.finalApprovalStatus) = LOWER(:finalApprovalStatus) " +
            "     OR :finalApprovalStatus IS NULL)")
    Page<FinalApprovalDTO> findFinalApprovalDTOsWithFilters(
            @Param("nipKaryawanResign") String nipKaryawanResign,
            @Param("namaKaryawan") String namaKaryawan,
            @Param("finalApprovalStatus") String finalApprovalStatus,
            Pageable pageable);


}
