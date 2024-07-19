package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApprovalTreasuryRepository extends JpaRepository<ApprovalTreasury, Long> {

    Optional<ApprovalTreasury> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalTreasury a WHERE a.id = :id")
    ApprovalAtasan findByApprovalTreasuryId(@Param("id") Long id);

    Optional <ApprovalTreasury> findByApprovalAtasanId(Long id);

    Page<ApprovalTreasury> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalTreasuryStatusIsOrApprovalTreasuryStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalTreasuryStatus,
            Pageable pageable
    );
}
