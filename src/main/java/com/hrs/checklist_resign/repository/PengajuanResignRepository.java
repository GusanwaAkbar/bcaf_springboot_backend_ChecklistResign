package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PengajuanResignRepository extends JpaRepository<PengajuanResign, Long> {
    Optional<PengajuanResign> findByUserDetailResign(UserDetail userDetailResign);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_bucket_approval_atasan WHERE pengajuan_resign_id = :id", nativeQuery = true)
    void deleteApprovalAtasanByResignationId(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_hrs_resign_bucket_pengajuan_resign WHERE id = :id", nativeQuery = true)
    void deletePengajuanResignById(Long id);

    Optional<PengajuanResign> findByNipUser(String nipUser);
}
