package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalGeneralServicesRepository extends JpaRepository<ApprovalGeneralServices, Long> {
}