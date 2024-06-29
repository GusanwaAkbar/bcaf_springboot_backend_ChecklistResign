package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalHRTalentRepository extends JpaRepository<ApprovalHRTalent, Long> {

    Optional<ApprovalHRTalent> findByNipKaryawanResign(String nipKaryawanResign);

}
