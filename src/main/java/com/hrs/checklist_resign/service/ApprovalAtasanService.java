package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.repository.ApprovalAtasanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalAtasanService {

    @Autowired
    private ApprovalAtasanRepository approvalAtasanRepository;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalAtasan";


    public ApprovalAtasan saveApproval(ApprovalAtasan approvalAtasan) {
        return approvalAtasanRepository.save(approvalAtasan);
    }
    public Optional<ApprovalAtasan> findByNipKaryawanResign(String nipKaryawanResign) {
        return approvalAtasanRepository.findByNipKaryawanResign(nipKaryawanResign);
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

    public ApprovalAtasan handleFileUpload(ApprovalAtasan approvalAtasan, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path);

        approvalAtasan.setDocumentPath(path.toString());
        return saveApproval(approvalAtasan);
    }




}