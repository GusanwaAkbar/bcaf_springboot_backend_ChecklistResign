package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRPayrollRepository extends JpaRepository<ApprovalHRPayroll, Long> {
    Optional<ApprovalHRPayroll> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRPayroll a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRPayrollId(@Param("id") Long id);

    Optional <ApprovalHRPayroll> findByApprovalAtasanId(Long id);


}
