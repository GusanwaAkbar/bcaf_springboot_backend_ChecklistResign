package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalSecurityAdministrator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalSecurityAdministratorRepository extends JpaRepository <ApprovalSecurityAdministrator, Long> {

    Optional<ApprovalSecurityAdministrator> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalSecurityAdministrator a WHERE a.id = :id")
    ApprovalAtasan findByApprovalSecurityAdministratorId(@Param("id") Long id);


    Optional <ApprovalSecurityAdministrator> findByApprovalAtasanId(Long id);

    Page<ApprovalSecurityAdministrator> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalSecurityAdministratorStatusIsOrApprovalSecurityAdministratorStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalSecurityAdministratorStatus,
            Pageable pageable
    );

    Page<ApprovalSecurityAdministrator> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalSecurityAdministratorStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalSecurityAdministratorStatus,
            Pageable pageable
    );


    @Query("SELECT a FROM ApprovalSecurityAdministrator a WHERE " +
            "(:nipKaryawanResign IS NULL OR LOWER(a.nipKaryawanResign) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) AND " +
            "(:namaKaryawan IS NULL OR LOWER(a.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
            "(:approvalStatus = 'null' AND a.approvalSecurityAdministratorStatus IS NULL OR " +
            ":approvalStatus != 'null' AND LOWER(a.approvalSecurityAdministratorStatus) = LOWER(:approvalStatus) OR " +
            ":approvalStatus IS NULL)")
    Page<ApprovalSecurityAdministrator> findWithFilters(
            @Param("nipKaryawanResign") String nipKaryawanResign,
            @Param("namaKaryawan") String namaKaryawan,
            @Param("approvalStatus") String approvalStatus,
            Pageable pageable
    );






}
