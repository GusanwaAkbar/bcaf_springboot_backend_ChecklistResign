package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.interfaces.ApprovalService;
import com.hrs.checklist_resign.repository.ApprovalHRTalentRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRTalentService implements ApprovalService {

    @Autowired
    ApprovalHRTalentRepository approvalHRTalentRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalHRTalent";

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    public ApprovalHRTalent saveApprovalHRTalent (ApprovalHRTalent approvalHRTalent)
    {
        return approvalHRTalentRepository.save(approvalHRTalent);
    }


    @Override
    public Optional<ApprovalHRTalent> findByNipKaryawanResign(String nipKaryawanResign) {
        return approvalHRTalentRepository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public void deleteApprovalHRTalent (Long id)
    {
        approvalHRTalentRepository.deleteById(id);
    }

    public Optional<ApprovalHRTalent> findById (Long id)
    {
        return approvalHRTalentRepository.findById(id);
    }

    public List<ApprovalHRTalent> findAll ()
    {
        return approvalHRTalentRepository.findAll();
    }

    public ResponseEntity<?> update(Long id, ApprovalHRTalent approvalHRTalentDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String nipApprover = authentication.getName();

        UserDetail userDetailAtasan =  userDetailsService.findByUsername(nipApprover);
        String namaApprover = userDetailAtasan.getNama();
        // End Authentication checking

        // Get existing item
        Optional<ApprovalHRTalent> approvalHRTalentOptional = approvalHRTalentRepository.findById(id);
        if (!approvalHRTalentOptional.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Approval HR Talent Not Found", HttpStatus.NOT_FOUND.value(), "Approval HR Talent with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRTalent existingApprovalHRTalent = approvalHRTalentOptional.get();
        // Update fields
        existingApprovalHRTalent.setPengecekanBiaya(approvalHRTalentDetails.getPengecekanBiaya());
        existingApprovalHRTalent.setApprovalHRTalentStatus(approvalHRTalentDetails.getApprovalHRTalentStatus());
        existingApprovalHRTalent.setRemarks(approvalHRTalentDetails.getRemarks());

        if (existingApprovalHRTalent.getApprovalHRTalentStatus().equals("accept"))
        {
            existingApprovalHRTalent.setApprovedDate(new Date());
            existingApprovalHRTalent.setApprovedBy(namaApprover);
        }

        //save the instance
        ApprovalHRTalent approvalHRTalent = approvalHRTalentRepository.save(existingApprovalHRTalent);

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id, "HRTALENT");

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }

        ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(approvalHRTalent, true, "Approval HR Talent Updated", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

        public ApprovalHRTalent handleFileUpload(ApprovalHRTalent approvalHRTalent, MultipartFile file) throws IOException
        {

            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), path);

            approvalHRTalent.setDocumentPath(path.toString());
            return saveApprovalHRTalent(approvalHRTalent);
        }

    public Page<ApprovalHRTalent> findAllWithFiltersAndPagination(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRTalentStatus,
            int page,
            int size,
            String sortBy,
            String sortDirection) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return approvalHRTalentRepository.findWithFilters(
                nipKaryawanResign,
                namaKaryawan,
                approvalHRTalentStatus,
                pageable
        );
    }




}

