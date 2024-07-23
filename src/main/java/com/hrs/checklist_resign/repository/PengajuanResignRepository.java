package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PengajuanResignRepository extends JpaRepository<PengajuanResign, Long> {
    Optional<PengajuanResign> findByUserDetailResign(UserDetail userDetailResign);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_final_approval WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteFinalApprovalByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_treasury WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalTreasuryByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_securityadministrator WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalSecurityAdministratorByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_hrtalent WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalHRTalentByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hrs_app_resign_approval_hrservicesadmin WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalHRServicesAdminByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_hrpayroll WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalHRPayrollByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_hrlearning WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalHRLearningByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_hrir WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalHRIRByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_approval_generalservices WHERE nip_karyawan_resign = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalGeneralServicesByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_bucket_approval_atasan WHERE nip_karyawan = :nipKaryawanResign", nativeQuery = true)
    void deleteApprovalAtasanByNipKaryawanResign(String nipKaryawanResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_bucket_pengajuan_resign WHERE nip_user = :nipUser", nativeQuery = true)
    void deletePengajuanResignByNipUser(String nipUser);
    Optional<PengajuanResign> findByNipUser(String nipUser);



        @Query("SELECT p FROM PengajuanResign p WHERE " +
                "(:nipKaryawan IS NULL OR LOWER(p.nipUser) LIKE LOWER(CONCAT('%', :nipKaryawan, '%'))) AND " +
                "(:namaKaryawan IS NULL OR LOWER(p.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
                "(:filter = 1 AND p.approvedDate IS NULL OR " +
                ":filter = 2 AND p.approvedDateAllDepartement IS NULL OR " +
                ":filter = 3 AND p.approvedDateFinal IS NULL OR " +
                ":filter IS NULL)")
        Page<PengajuanResign> findWithFilters(
                @Param("nipKaryawan") String nipKaryawan,
                @Param("namaKaryawan") String namaKaryawan,
                @Param("filter") Integer filter,
                Pageable pageable
        );

}
