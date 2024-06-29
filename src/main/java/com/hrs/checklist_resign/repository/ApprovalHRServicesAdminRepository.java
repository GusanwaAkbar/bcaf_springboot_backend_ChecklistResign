package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRServicesAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalHRServicesAdminRepository extends JpaRepository <ApprovalHRServicesAdmin, Long> {
    Optional<ApprovalHRServicesAdmin> findByNipKaryawanResign(String nipKaryawanResign);
}
