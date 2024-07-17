package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApprovalHRLearningRepository extends JpaRepository <ApprovalHRLearning, Long> {

    Optional<ApprovalHRLearning> findByNipKaryawanResign(String nipKaryawanResign);

    @Query("SELECT a.approvalAtasan FROM ApprovalHRLearning a WHERE a.id = :id")
    ApprovalAtasan findByApprovalHRLearningId(@Param("id") Long id);

    Optional <ApprovalHRLearning> findByApprovalAtasanId(Long id);
}
