package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
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
}