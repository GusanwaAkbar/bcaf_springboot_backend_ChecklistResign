package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalGeneralServicesRepository extends JpaRepository<ApprovalGeneralServices, Long> {

    Optional<ApprovalGeneralServices> findByNipKaryawanResign(String nipKaryawanResign);
}