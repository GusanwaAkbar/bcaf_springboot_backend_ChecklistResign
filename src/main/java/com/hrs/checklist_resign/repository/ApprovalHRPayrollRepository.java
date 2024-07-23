package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRPayrollRepository extends JpaRepository<ApprovalHRPayroll, Long> {
    Optional<ApprovalHRPayroll> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRPayroll a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRPayrollId(@Param("id") Long id);

    Optional <ApprovalHRPayroll> findByApprovalAtasanId(Long id);

    Page<ApprovalHRPayroll> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRPayrollStatusIsOrApprovalHRPayrollStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRPayrollStatus,
            Pageable pageable
    );

    Page<ApprovalHRPayroll> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRPayrollStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRPayrollStatus,
            Pageable pageable
    );


    @Query("SELECT a FROM ApprovalHRPayroll a WHERE " +
            "(:nipKaryawanResign IS NULL OR LOWER(a.nipKaryawanResign) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) AND " +
            "(:namaKaryawan IS NULL OR LOWER(a.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
            "(:approvalStatus = 'null' AND a.approvalHRPayrollStatus IS NULL OR " +
            ":approvalStatus != 'null' AND LOWER(a.approvalHRPayrollStatus) = LOWER(:approvalStatus) OR " +
            ":approvalStatus IS NULL)")
    Page<ApprovalHRPayroll> findWithFilters(
            @Param("nipKaryawanResign") String nipKaryawanResign,
            @Param("namaKaryawan") String namaKaryawan,
            @Param("approvalStatus") String approvalStatus,
            Pageable pageable
    );





}
