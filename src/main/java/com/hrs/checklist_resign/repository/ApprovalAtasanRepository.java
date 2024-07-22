package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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



}

