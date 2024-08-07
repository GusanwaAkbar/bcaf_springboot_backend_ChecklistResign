package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRLearningRepository extends JpaRepository <ApprovalHRLearning, Long> {

    Optional<ApprovalHRLearning> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRLearning a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRLearningId(@Param("id") Long id);

    Optional <ApprovalHRLearning> findByApprovalAtasanId(Long id);

    Page<ApprovalHRLearning> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRLearningStatusIsOrApprovalHRLearningStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRLearningStatus,
            Pageable pageable
    );

    Page<ApprovalHRLearning> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRLearningStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRLearningStatus,
            Pageable pageable
    );

    @Query("SELECT a FROM ApprovalHRLearning a WHERE " +
            "(:nipKaryawanResign IS NULL OR LOWER(a.nipKaryawanResign) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) AND " +
            "(:namaKaryawan IS NULL OR LOWER(a.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
            "(:approvalStatus = 'null' AND a.approvalHRLearningStatus IS NULL OR " +
            ":approvalStatus != 'null' AND LOWER(a.approvalHRLearningStatus) = LOWER(:approvalStatus) OR " +
            ":approvalStatus IS NULL)")
    Page<ApprovalHRLearning> findWithFilters(
            @Param("nipKaryawanResign") String nipKaryawanResign,
            @Param("namaKaryawan") String namaKaryawan,
            @Param("approvalStatus") String approvalStatus,
            Pageable pageable
    );



}
