package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalGeneralServicesRepository extends JpaRepository<ApprovalGeneralServices, Long> {

    Optional<ApprovalGeneralServices> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalGeneralServices a WHERE a.id = :id")
    ApprovalAtasan findByGeneralServicesId(@Param("id") Long id);


    Optional <ApprovalGeneralServices> findByApprovalAtasanId(Long id);

    Page<ApprovalGeneralServices> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalGeneralServicesStatusIsOrApprovalGeneralServicesStatusIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalGeneralServicesStatus,
            Pageable pageable
    );

    Page<ApprovalGeneralServices> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalGeneralServicesStatusContainingIgnoreCase(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalGeneralServicesStatus,
            Pageable pageable
    );



    @Query("SELECT a FROM ApprovalGeneralServices a WHERE " +
            "(:nipKaryawanResign IS NULL OR LOWER(a.nipKaryawanResign) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) AND " +
            "(:namaKaryawan IS NULL OR LOWER(a.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
            "(:approvalStatus = 'null' AND a.approvalGeneralServicesStatus IS NULL OR " +
            ":approvalStatus != 'null' AND LOWER(a.approvalGeneralServicesStatus) = LOWER(:approvalStatus) OR " +
            ":approvalStatus IS NULL)")
    Page<ApprovalGeneralServices> findWithFilters(
            @Param("nipKaryawanResign") String nipKaryawanResign,
            @Param("namaKaryawan") String namaKaryawan,
            @Param("approvalStatus") String approvalStatus,
            Pageable pageable
    );


}