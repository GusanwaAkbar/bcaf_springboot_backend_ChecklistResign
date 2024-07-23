package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRServicesAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRServicesAdminRepository extends JpaRepository <ApprovalHRServicesAdmin, Long> {
    Optional<ApprovalHRServicesAdmin> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRServicesAdmin a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRServicesAdminId(@Param("id") Long id);


    Optional <ApprovalHRServicesAdmin> findByApprovalAtasanId(Long id);

    Page<ApprovalHRServicesAdmin> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRServicesAdminStatusIsOrApprovalHRServicesAdminStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRServicesAdminStatus,
            Pageable pageable
    );

    Page<ApprovalHRServicesAdmin> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRServicesAdminStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRServicesAdminStatus,
            Pageable pageable
    );

    @Query("SELECT a FROM ApprovalHRServicesAdmin a WHERE " +
            "(:nipKaryawanResign IS NULL OR LOWER(a.nipKaryawanResign) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) AND " +
            "(:namaKaryawan IS NULL OR LOWER(a.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
            "(:approvalStatus = 'null' AND a.approvalHRServicesAdminStatus IS NULL OR " +
            ":approvalStatus != 'null' AND LOWER(a.approvalHRServicesAdminStatus) = LOWER(:approvalStatus) OR " +
            ":approvalStatus IS NULL)")
    Page<ApprovalHRServicesAdmin> findWithFilters(
            @Param("nipKaryawanResign") String nipKaryawanResign,
            @Param("namaKaryawan") String namaKaryawan,
            @Param("approvalStatus") String approvalStatus,
            Pageable pageable
    );



}
