package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalHRPayrollRepository extends JpaRepository<ApprovalHRPayroll, Long> {
    Optional<ApprovalHRPayroll> findByNipKaryawanResign(String nipKaryawanResign);
}
