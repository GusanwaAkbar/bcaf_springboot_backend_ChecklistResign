package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.FinalApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalApprovalRepository extends JpaRepository<FinalApproval, Long> {
}
