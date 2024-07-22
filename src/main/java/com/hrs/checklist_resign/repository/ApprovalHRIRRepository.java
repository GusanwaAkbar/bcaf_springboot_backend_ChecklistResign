package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRIRRepository extends JpaRepository<ApprovalHRIR, Long> {

    Optional<ApprovalHRIR> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRIR a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRIRId(@Param("id") Long id);

    Optional <ApprovalHRIR> findByApprovalAtasanId(Long id);

    Page<ApprovalHRIR> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRIRStatusIsOrApprovalHRIRStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRIRStatus,
            Pageable pageable
    );

    Page<ApprovalHRIR> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRIRStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRIRStatus,
            Pageable pageable
    );



}
