package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.repository.ApprovalAtasanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalAtasanService {

    @Autowired
    private ApprovalAtasanRepository approvalAtasanRepository;

    public ApprovalAtasan saveApproval(ApprovalAtasan approvalAtasan) {
        return approvalAtasanRepository.save(approvalAtasan);
    }

    public Optional<ApprovalAtasan> findById(Long id) {
        return approvalAtasanRepository.findById(id);
    }

    public List<ApprovalAtasan> findAll() {
        return approvalAtasanRepository.findAll();
    }

    public void deleteById(Long id) {
        approvalAtasanRepository.deleteById(id);
    }

    public List<ApprovalAtasan> getApprovalByNipAtasan(String nipAtasan) {
        return approvalAtasanRepository.findByNipAtasan(nipAtasan);
    }
}