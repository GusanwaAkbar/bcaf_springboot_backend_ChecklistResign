package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.dto.UserDetailDTO;
import com.hrs.checklist_resign.dto.UserResponseDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    @Autowired
    private PengajuanResignService pengajuanResignService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private NotificationService notificationService;



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

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalAtasan>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalAtasan> approvalAtasan = approvalAtasanService.findByNipKaryawanResign(nipKaryawanResign);



        if (approvalAtasan.isPresent()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(approvalAtasan.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalAtasan not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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


    @GetMapping("/get-approval-by-username")
    public ResponseEntity<ApiResponse<List<ApprovalAtasan>>> getApprovalByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipAtasan = authentication.getName();
        List<ApprovalAtasan> approvalAtasanList = approvalAtasanService.getApprovalByNipAtasan(nipAtasan);

        if (approvalAtasanList.isEmpty()) {
            ApiResponse response = new ApiResponse<>(true, "No data found", HttpStatus.OK.value(), "No approval found for the given username");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ApiResponse response = new ApiResponse<>(approvalAtasanList,true, "Data retrieved successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-approval-by-username/{id}")
    public ResponseEntity<ApiResponse<List<ApprovalAtasan>>> getApprovalByUsername(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipAtasan = authentication.getName();
        List<ApprovalAtasan> approvalAtasanList = approvalAtasanService.getApprovalByNipAtasan(nipAtasan);

        Optional<ApprovalAtasan> approvalAtasan = approvalAtasanService.findById(id);

        if (approvalAtasan.isPresent())
        {

            //approvalatasan is present
            if (approvalAtasanList.contains(approvalAtasan))
            {
                ApiResponse response = new ApiResponse<>(approvalAtasan, true, "Approval atasan successfully fetched", HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            // user is try to grab others data
            else
            {

                ApiResponse response = new ApiResponse<>(false, "User don't have right to open this item", HttpStatus.UNAUTHORIZED.value(), "Login with different account");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

            }
        }


        if (approvalAtasanList.isEmpty()) {
            ApiResponse response = new ApiResponse<>(true, "No data found", HttpStatus.OK.value(), "No approval found for the given username");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ApiResponse response = new ApiResponse<>(false, "Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PutMapping(value = "/get-approval-by-username/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
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

        String nipAtasan = authentication.getName();

        // Find the approval by ID instead of by nipAtasan
        Optional<ApprovalAtasan> approvalAtasanOptional = approvalAtasanService.findById(id);

        if (!approvalAtasanOptional.isPresent() || !approvalAtasanOptional.get().getNipAtasan().equals(nipAtasan)) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "Update failed", HttpStatus.NOT_FOUND.value(), "No approval found with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalAtasan approvalAtasan = approvalAtasanOptional.get();


        //Setup nip karyawan
        String nipKaryawan = approvalAtasan.getPengajuanResign().getUserDetailResign().getUserUsername();

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


            //set approved date on pengajuan resign
            Optional<PengajuanResign> pengajuanResignOpt = pengajuanResignService.getResignationByNipUser(approvalAtasan.getNipKaryawanResign());
            PengajuanResign pengajuanResign = pengajuanResignOpt.get();
            pengajuanResign.setApprovedDate(new Date());
            pengajuanResignService.saveResignation(pengajuanResign);

            //set approved date on approval atasan
            approvalAtasan.setApprovedDate(new Date());
            approvalAtasanService.saveApproval(approvalAtasan);




            ApprovalHRTalent approvalHRTalent = new ApprovalHRTalent();
            approvalHRTalent.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRTalent.setNipKaryawanResign(nipKaryawan);
            approvalHRTalentService.saveApprovalHRTalent(approvalHRTalent);

            ApprovalHRIR approvalHRIR = new ApprovalHRIR();
            approvalHRIR.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRIR.setNipKaryawanResign(nipKaryawan);
            approvalHRIRService.saveApprovalHRIR(approvalHRIR);

            ApprovalTreasury approvalTreasury = new ApprovalTreasury();
            approvalTreasury.setApprovalAtasan(updatedApprovalAtasan);
            approvalTreasury.setNipKaryawanResign(nipKaryawan);
            approvalTreasuryService.save(approvalTreasury);

            ApprovalHRServicesAdmin approvalHRServicesAdmin = new ApprovalHRServicesAdmin();
            approvalHRServicesAdmin.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRServicesAdmin.setNipKaryawanResign(nipKaryawan);
            approvalHRServicesAdminService.save(approvalHRServicesAdmin);

            ApprovalHRPayroll approvalHRPayroll = new ApprovalHRPayroll();
            approvalHRPayroll.setApprovalAtasan(updatedApprovalAtasan);
            approvalHRPayroll.setNipKaryawanResign(nipKaryawan);
            approvalHRPayrollService.save(approvalHRPayroll);

            ApprovalSecurityAdministrator approvalSecurityAdministrator = new ApprovalSecurityAdministrator();
            approvalSecurityAdministrator.setApprovalAtasan(updatedApprovalAtasan);
            approvalSecurityAdministrator.setNipKaryawanResign(nipKaryawan);
            approvalSecurityAdministratorService.save(approvalSecurityAdministrator);

            ApprovalGeneralServices approvalGeneralServices = new ApprovalGeneralServices();
            approvalGeneralServices.setApprovalAtasan(updatedApprovalAtasan);
            approvalGeneralServices.setNipKaryawanResign(nipKaryawan);
            approvalGeneralServicesService.save(approvalGeneralServices);

            ApprovalHRLearning approvalHRLearning = new ApprovalHRLearning();
            approvalHRLearning.setApprovalAtasan(approvalAtasan);
            approvalHRLearning.setNipKaryawanResign(nipKaryawan);
            approvalHRLearningService.save(approvalHRLearning);

            List<UserResponseDTO> userAdmin = adminService.findUsersWithRolesNotContainingV2("USER");

            UserDetail userDetailKaryawan = approvalAtasan.getPengajuanResign().getUserDetailResign();

            // Send HTML email and notification to users with roles not containing "USER"
            String subject = "Approval Required: New Resignation Request";
            String message = "Approval Required: New Resignation Request";
            String link = "http://your-application-link.com";  // Adjust the link accordingly

            for (UserResponseDTO user : userAdmin) {

                System.out.println(user);

                UserDetail userDetailAdmin = user.getUserDetails();

                sendNotificationsAndEmails(userDetailKaryawan, userDetailAdmin, nipKaryawan );

            }

            String userNama = userDetailKaryawan.getNama();

            notificationService.sendNotification("Resignation Request has been approved by Atasan, check the link below for more details", userDetailKaryawan, nipKaryawan);
            Map<String, Object> variablesKaryawan = emailTemplateService.createEmailVariables(userNama, "Resignation Request has been approved by Atasan, check the link below for more details.", "http://localhost:4200/#/progress-approval");


        }

        ApiResponse<ApprovalAtasan> response = new ApiResponse<>(updatedApprovalAtasan, true, "Approval Atasan updated successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    private void sendNotificationsAndEmails(UserDetail userDetail, UserDetail userDetailAtasan, String nipKaryawanResign) {
        String emailAtasan = userDetailAtasan.getEmail();
        String userEmail = userDetail.getEmail();
        String userNama = userDetail.getNama();
        String atasanNama = userDetailAtasan.getNama();

        notificationService.sendNotification("Approval Required: New Resignation Request: " + nipKaryawanResign + ", " + userNama, userDetail, userDetailAtasan.getUserUsername());


        //String linkKaryawan = "http://localhost:4200/#/progress-approval";
        String linkAtasan = "http://localhost:4200/#/";


        Map<String, Object> variablesAtasan = emailTemplateService.createEmailVariables(atasanNama, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, linkAtasan);

        try {
           // emailTemplateService.sendHtmlEmail(userEmail, "Resignation Request has been approved by Atasan", "email-template", variablesKaryawan);
            emailTemplateService.sendHtmlEmail(emailAtasan, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, "email-template", variablesAtasan);
        } catch (MessagingException e) {

            System.out.println("gagal mengirim email");
            e.printStackTrace();
            // Handle exception
        }
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

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalAtasan>> uploadFile(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalAtasan> approvalAtasanOpt = approvalAtasanService.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalAtasan approvalAtasan = approvalAtasanOpt.get();

        if (approvalAtasan == null) {
            throw new RuntimeException("ApprovalAtasan not found");
        }

        try {
            ApprovalAtasan updatedApprovalAtasan = approvalAtasanService.handleFileUpload(approvalAtasan, file);
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(updatedApprovalAtasan, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}










