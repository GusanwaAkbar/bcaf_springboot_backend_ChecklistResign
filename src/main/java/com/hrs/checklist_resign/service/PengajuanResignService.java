package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.repository.PengajuanResignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PengajuanResignService {

    @Autowired
    private PengajuanResignRepository pengajuanResignRepository;

    public List<PengajuanResign> getAllResignations() {
        return pengajuanResignRepository.findAll();
    }

    public Optional<PengajuanResign> getResignationById(Long id) {
        return pengajuanResignRepository.findById(id);
    }

    public PengajuanResign saveResignation(PengajuanResign pengajuanResign) {
        return pengajuanResignRepository.save(pengajuanResign);
    }

    public void deleteResignation(Long id) {
        pengajuanResignRepository.deleteById(id);
    }
}
