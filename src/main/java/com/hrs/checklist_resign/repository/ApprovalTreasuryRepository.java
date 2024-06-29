package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalTreasuryRepository extends JpaRepository<ApprovalTreasury, Long> {

    Optional<ApprovalTreasury> findByNipKaryawanResign(String nipKaryawanResign);
}
