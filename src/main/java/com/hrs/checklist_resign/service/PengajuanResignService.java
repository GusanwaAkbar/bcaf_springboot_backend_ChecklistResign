package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.ApprovalAtasanRepository;
import com.hrs.checklist_resign.repository.PengajuanResignRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PengajuanResignService {

    @Autowired
    private PengajuanResignRepository pengajuanResignRepository;

    @Autowired
    private ApprovalAtasanRepository approvalAtasanRepository;

    public List<PengajuanResign> getAllResignations() {
        return pengajuanResignRepository.findAll();
    }

    public Optional<PengajuanResign> getResignationById(Long id) {
        return pengajuanResignRepository.findById(id);
    }

    public Optional<PengajuanResign> getResignationByUserDetail(UserDetail userDetail) {
        return pengajuanResignRepository.findByUserDetailResign(userDetail);
    }

    public PengajuanResign saveResignation(PengajuanResign pengajuanResign) {
        return pengajuanResignRepository.save(pengajuanResign);
    }

    @Transactional
    public void deleteResignation(Long id) {
        // Delete related entities first
        pengajuanResignRepository.deleteApprovalAtasanByResignationId(id);

        // Delete the PengajuanResign entity
        pengajuanResignRepository.deletePengajuanResignById(id);
    }
}
