package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.PengajuanResign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengajuanResignRepository extends JpaRepository<PengajuanResign, Long> {
}
