package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApprovalAtasanRepository extends JpaRepository<ApprovalAtasan, Long> {

    List<ApprovalAtasan> findByNipAtasan(String nipAtasan);

    Optional<ApprovalAtasan> findByNipKaryawanResign(String nipKaryawanResign);


    Page<ApprovalAtasan> findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalStatusAtasanIsOrApprovalStatusAtasanIsNull(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalStatusAtasan,
            Pageable pageable
    );




        @Query("SELECT a FROM ApprovalAtasan a WHERE " +
                "(:nipKaryawanResign IS NULL OR LOWER(a.nipKaryawanResign) LIKE LOWER(CONCAT('%', :nipKaryawanResign, '%'))) AND " +
                "(:namaKaryawan IS NULL OR LOWER(a.namaKaryawan) LIKE LOWER(CONCAT('%', :namaKaryawan, '%'))) AND " +
                "(:approvalStatus = 'null' AND a.approvalStatusAtasan IS NULL OR " +
                ":approvalStatus != 'null' AND LOWER(a.approvalStatusAtasan) = LOWER(:approvalStatus) OR " +
                ":approvalStatus IS NULL) AND " +
                "a.nipAtasan = :nipAtasan")
        Page<ApprovalAtasan> findWithFilters(
                @Param("nipKaryawanResign") String nipKaryawanResign,
                @Param("namaKaryawan") String namaKaryawan,
                @Param("approvalStatus") String approvalStatus,
                @Param("nipAtasan") String nipAtasan,
                Pageable pageable
        );






}

