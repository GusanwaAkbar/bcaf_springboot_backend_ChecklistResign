package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-atasan")
public class ApprovalAtasanController {

    @Autowired
    private ApprovalAtasanService approvalAtasanService;

    @Autowired
    private ApprovalHRTalentService approvalHRTalentService;

    @Autowired
    private ApprovalHRIRService approvalHRIRService;

    @Autowired
    private ApprovalGeneralServicesService approvalGeneralServicesService;

    @Autowired
    private ApprovalHRPayrollService approvalHRPayrollService;

    @Autowired
    private ApprovalHRServicesAdminService approvalHRServicesAdminService;

    @Autowired
    private ApprovalSecurityAdministratorService approvalSecurityAdministratorService;

    @Autowired
    private ApprovalTreasuryService approvalTreasuryService;

    @Autowired
    private ApprovalHRLearningService approvalHRLearningService;




    @PostMapping()
    public ResponseEntity<ApprovalAtasan> createApproval(@RequestBody ApprovalAtasan approvalAtasan) {
        ApprovalAtasan createdApprovalAtasan = approvalAtasanService.saveApproval(approvalAtasan);

        // make approval hr talent entity and one to one if the approval status equal 1
        if (approvalAtasan.getApprovalStatusAtasan() == "accept")
        {
            ApprovalHRTalent approvalHRTalent = new ApprovalHRTalent();
            approvalHRTalent.setApprovalAtasan(approvalAtasan);
        }

        // createdHRTalent =


        return ResponseEntity.ok(createdApprovalAtasan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApproval(@PathVariable Long id) {

        //Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        //End Authententication checking


        return approvalAtasanService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalAtasan>>> getAllApproval() {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<List<ApprovalAtasan>> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        List<ApprovalAtasan> approvalAtasanList = approvalAtasanService.findAll();
        ApiResponse<List<ApprovalAtasan>> response = new ApiResponse<>(approvalAtasanList, true, "Approvals fetched successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ApprovalAtasan>> updateApprovalAtasan(
            @PathVariable Long id,
            @RequestBody ApprovalAtasan approvalAtasanDetails) {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        Optional<ApprovalAtasan> approvalAtasanOptional = approvalAtasanService.findById(id);

        if (!approvalAtasanOptional.isPresent()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "Update failed", HttpStatus.NOT_FOUND.value(), "No approval found with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalAtasan approvalAtasan = approvalAtasanOptional.get();
        approvalAtasan.setSerahTerimaTugas(approvalAtasanDetails.getSerahTerimaTugas());
        approvalAtasan.setPengembalianNotebook(approvalAtasanDetails.getPengembalianNotebook());
        approvalAtasan.setPengembalianKunciLoker(approvalAtasanDetails.getPengembalianKunciLoker());
        approvalAtasan.setPengembalianKunciRuangan(approvalAtasanDetails.getPengembalianKunciRuangan());
        approvalAtasan.setPenyerahanSuratPengunduranDiri(approvalAtasanDetails.getPenyerahanSuratPengunduranDiri());
        approvalAtasan.setPengembalianIdCard(approvalAtasanDetails.getPengembalianIdCard());
        approvalAtasan.setHapusAplikasiMobile(approvalAtasanDetails.getHapusAplikasiMobile());
        approvalAtasan.setUninstallSoftwareNotebook(approvalAtasanDetails.getUninstallSoftwareNotebook());
        approvalAtasan.setUninstallSoftwareUnitKerja(approvalAtasanDetails.getUninstallSoftwareUnitKerja());
        approvalAtasan.setApprovalStatusAtasan(approvalAtasanDetails.getApprovalStatusAtasan());
        approvalAtasan.setRemarksAtasan(approvalAtasanDetails.getRemarksAtasan());

        ApprovalAtasan updatedApprovalAtasan = approvalAtasanService.saveApproval(approvalAtasan);

        if (approvalAtasanDetails.getApprovalStatusAtasan().equals("accept")) {
            ApprovalHRTalent approvalHRTalent = new ApprovalHRTalent();
            approvalHRTalent.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRTalentService.saveApprovalHRTalent(approvalHRTalent);

            ApprovalHRIR approvalHRIR = new ApprovalHRIR();
            approvalHRIR.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRIRService.saveApprovalHRIR(approvalHRIR);

            ApprovalTreasury approvalTreasury = new ApprovalTreasury();
            approvalTreasury.setApprovalAtasan((updatedApprovalAtasan));
            approvalTreasuryService.save(approvalTreasury);

            ApprovalHRServicesAdmin approvalHRServicesAdmin= new ApprovalHRServicesAdmin();
            approvalHRServicesAdmin.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRServicesAdminService.save(approvalHRServicesAdmin);

            ApprovalHRPayroll approvalHRPayroll = new ApprovalHRPayroll();
            approvalHRPayroll.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRPayrollService.save(approvalHRPayroll);

            ApprovalSecurityAdministrator approvalSecurityAdministrator = new ApprovalSecurityAdministrator();
            approvalSecurityAdministrator.setApprovalAtasan(updatedApprovalAtasan);
            approvalSecurityAdministratorService.save(approvalSecurityAdministrator);

            ApprovalGeneralServices approvalGeneralServices = new ApprovalGeneralServices();
            approvalGeneralServices.setApprovalAtasan(updatedApprovalAtasan);
            approvalGeneralServicesService.save(approvalGeneralServices);

            ApprovalHRLearning approvalHRLearning = new ApprovalHRLearning();
            approvalHRLearning.setApprovalAtasan(approvalAtasan);
            approvalHRLearningService.save(approvalHRLearning);





        }

        ApiResponse<ApprovalAtasan> response = new ApiResponse<>(updatedApprovalAtasan, true, "Approval Atasan updated successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApproval(@PathVariable Long id) {

        //Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        //End Authententication checking

        return approvalAtasanService.findById(id).map(approvalAtasan -> {
            approvalAtasanService.deleteById(id);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}










