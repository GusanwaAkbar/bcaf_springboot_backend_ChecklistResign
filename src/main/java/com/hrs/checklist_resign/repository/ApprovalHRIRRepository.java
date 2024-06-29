package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalHRIRRepository extends JpaRepository<ApprovalHRIR, Long> {

    Optional<ApprovalHRIR> findByNipKaryawanResign(String nipKaryawanResign);

}
