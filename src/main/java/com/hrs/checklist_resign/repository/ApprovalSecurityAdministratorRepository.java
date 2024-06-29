package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalSecurityAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalSecurityAdministratorRepository extends JpaRepository <ApprovalSecurityAdministrator, Long> {

    Optional<ApprovalSecurityAdministrator> findByNipKaryawanResign(String nipKaryawanResign);
}
