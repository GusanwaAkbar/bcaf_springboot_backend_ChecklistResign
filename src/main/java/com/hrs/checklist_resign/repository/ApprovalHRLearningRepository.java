package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalHRLearningRepository extends JpaRepository <ApprovalHRLearning, Long> {

    Optional<ApprovalHRLearning> findByNipKaryawanResign(String nipKaryawanResign);
}
