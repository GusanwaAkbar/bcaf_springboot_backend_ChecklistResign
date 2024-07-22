package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRTalentRepository extends JpaRepository<ApprovalHRTalent, Long> {

    Optional<ApprovalHRTalent> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRTalent a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRTalentId(@Param("id") Long id);


    Optional <ApprovalHRTalent> findByApprovalAtasanId(Long id);

    Page<ApprovalHRTalent> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRTalentStatusIsOrApprovalHRTalentStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRTalentStatus,
            Pageable pageable
    );

    Page<ApprovalHRTalent> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRTalentStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRTalentStatus,
            Pageable pageable
    );



}
