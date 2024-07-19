package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.dto.UserResponseDTO;
import com.hrs.checklist_resign.repository.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CheckingAllApprovalsStatus {

    @Autowired
    private ApprovalTreasuryRepository approvalTreasuryRepository;

    @Autowired
    private ApprovalGeneralServicesRepository approvalGeneralServicesRepository;

    @Autowired
    private ApprovalHRIRRepository approvalHRIRRepository;

    @Autowired
    private ApprovalHRLearningRepository approvalHRLearningRepository;

    @Autowired
    private ApprovalHRPayrollRepository approvalHRPayrollRepository;

    @Autowired
    private ApprovalHRServicesAdminRepository approvalHRServicesAdminRepository;

    @Autowired
    private ApprovalHRTalentRepository approvalHRTalentRepository;

    @Autowired
    private ApprovalSecurityAdministratorRepository approvalSecurityAdministratorRepository;

    @Autowired
    private FinalApprovalRepository finalApprovalRepository;

    @Autowired
    private ApprovalAtasanRepository approvalAtasanRepository;

    @Autowired
    private  NotificationService notificationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private AsyncEmailService asyncEmailService;

    @Autowired
    private AdminService adminService;

    public boolean doCheck(Long id, String approvalName) {

        String nipKaryawan = "";

        if (approvalName.equals("TREASURY")) {
            Optional<ApprovalTreasury> approvalTreasuryOpt = approvalTreasuryRepository.findById(id);
            if (approvalTreasuryOpt.isPresent()) {
                ApprovalTreasury approvalTreasury = approvalTreasuryOpt.get();
                nipKaryawan = approvalTreasury.getNipKaryawanResign();
            }
        } else if (approvalName.equals("HRPAYROLL")) {
            Optional<ApprovalHRPayroll> approvalHRPayrollOpt = approvalHRPayrollRepository.findById(id);
            if (approvalHRPayrollOpt.isPresent()) {
                ApprovalHRPayroll approvalHRPayroll = approvalHRPayrollOpt.get();
                nipKaryawan = approvalHRPayroll.getNipKaryawanResign();
            }
        } else if (approvalName.equals("GENERALSERVICES")) {
            Optional<ApprovalGeneralServices> approvalGeneralServicesOpt = approvalGeneralServicesRepository.findById(id);
            if (approvalGeneralServicesOpt.isPresent()) {
                ApprovalGeneralServices approvalGeneralServices = approvalGeneralServicesOpt.get();
                nipKaryawan = approvalGeneralServices.getNipKaryawanResign();
            }
        } else if (approvalName.equals("HRIR")) {
            Optional<ApprovalHRIR> approvalHRIROpt = approvalHRIRRepository.findById(id);
            if (approvalHRIROpt.isPresent()) {
                ApprovalHRIR approvalHRIR = approvalHRIROpt.get();
                nipKaryawan = approvalHRIR.getNipKaryawanResign();
            }
        } else if (approvalName.equals("HRLEARNING")) {
            Optional<ApprovalHRLearning> approvalHRLearningOpt = approvalHRLearningRepository.findById(id);
            if (approvalHRLearningOpt.isPresent()) {
                ApprovalHRLearning approvalHRLearning = approvalHRLearningOpt.get();
                nipKaryawan = approvalHRLearning.getNipKaryawanResign();
            }
        } else if (approvalName.equals("HRSERVICESADMIN")) {
            Optional<ApprovalHRServicesAdmin> approvalHRServicesAdminOpt = approvalHRServicesAdminRepository.findById(id);
            if (approvalHRServicesAdminOpt.isPresent()) {
                ApprovalHRServicesAdmin approvalHRServicesAdmin = approvalHRServicesAdminOpt.get();
                nipKaryawan = approvalHRServicesAdmin.getNipKaryawanResign();
            }
        } else if (approvalName.equals("HRTALENT")) {
            Optional<ApprovalHRTalent> approvalHRTalentOpt = approvalHRTalentRepository.findById(id);
            if (approvalHRTalentOpt.isPresent()) {
                ApprovalHRTalent approvalHRTalent = approvalHRTalentOpt.get();
                nipKaryawan = approvalHRTalent.getNipKaryawanResign();
            }
        } else if (approvalName.equals("SECURITYADMINISTRATOR")) {
            Optional<ApprovalSecurityAdministrator> approvalSecurityAdministratorOpt = approvalSecurityAdministratorRepository.findById(id);
            if (approvalSecurityAdministratorOpt.isPresent()) {
                ApprovalSecurityAdministrator approvalSecurityAdministrator = approvalSecurityAdministratorOpt.get();
                nipKaryawan = approvalSecurityAdministrator.getNipKaryawanResign();
            }
        }

        // Fetch approval statuses by nipKaryawan
        String approvalHRPayrollStatus = approvalHRPayrollRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalHRPayroll::getApprovalHRPayrollStatus).orElse(null);
        String approvalGeneralServicesStatus = approvalGeneralServicesRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalGeneralServices::getApprovalGeneralServicesStatus).orElse(null);
        String approvalHRIRStatus = approvalHRIRRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalHRIR::getApprovalHRIRStatus).orElse(null);
        String approvalHRLearningStatus = approvalHRLearningRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalHRLearning::getApprovalHRLearningStatus).orElse(null);
        String approvalHRServicesAdminStatus = approvalHRServicesAdminRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalHRServicesAdmin::getApprovalHRServicesAdminStatus).orElse(null);
        String approvalHRTalentStatus = approvalHRTalentRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalHRTalent::getApprovalHRTalentStatus).orElse(null);
        String approvalSecurityAdministratorStatus = approvalSecurityAdministratorRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalSecurityAdministrator::getApprovalSecurityAdministratorStatus).orElse(null);
        String approvalTreasuryStatus = approvalTreasuryRepository.findByNipKaryawanResign(nipKaryawan).map(ApprovalTreasury::getApprovalTreasuryStatus).orElse(null);

        System.out.println("=========================== do checking ===========================");

        return isApprovalStatusAccepted(Optional.ofNullable(approvalHRPayrollStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalGeneralServicesStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalHRIRStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalSecurityAdministratorStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalTreasuryStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalHRTalentStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalHRServicesAdminStatus)) &&
                isApprovalStatusAccepted(Optional.ofNullable(approvalHRLearningStatus));
    }

    private boolean isApprovalStatusAccepted(Optional<String> status) {
        return status.isPresent() && "accept".equals(status.get());
    }

    public FinalApproval createFinalApproval(Long id) {

        //List<UserResponseDTO> userAdmin = adminService.findUsersWithRolesNotContainingV2("USER");

        System.out.println("=========================== do creating ===========================");

        FinalApproval finalApproval = new FinalApproval();

        UserDetail userDetailAtasan = approvalTreasuryRepository.findById(id).get().getApprovalAtasan().getUserDetailAtasan();
        UserDetail userDetailKaryawan = approvalTreasuryRepository.findById(id).get().getApprovalAtasan().getPengajuanResign().getUserDetailResign();

        finalApproval.setUserDetailAtasan(userDetailAtasan);

        finalApproval.setUserDetailResign(userDetailKaryawan);

        finalApproval.setApprovalAtasan(approvalTreasuryRepository.findById(id).get().getApprovalAtasan() );

        finalApproval.setPengajuanResign(approvalTreasuryRepository.findById(id).get().getApprovalAtasan().getPengajuanResign());

        finalApproval.setApprovalTreasury(approvalTreasuryRepository.findById(id).orElse(null));
        finalApproval.setApprovalGeneralServices(approvalGeneralServicesRepository.findById(id).orElse(null));
        finalApproval.setApprovalHRIR(approvalHRIRRepository.findById(id).orElse(null));
        finalApproval.setApprovalHRLearning(approvalHRLearningRepository.findById(id).orElse(null));
        finalApproval.setApprovalHRPayroll(approvalHRPayrollRepository.findById(id).orElse(null));
        finalApproval.setApprovalHRServicesAdmin(approvalHRServicesAdminRepository.findById(id).orElse(null));
        finalApproval.setApprovalHRTalent(approvalHRTalentRepository.findById(id).orElse(null));
        finalApproval.setApprovalSecurityAdministrator(approvalSecurityAdministratorRepository.findById(id).orElse(null));
        finalApproval.setFinalApprovalStatus("approved");
        finalApproval.setNipKaryawanResign(finalApproval.getUserDetailResign().getUserUsername());
        finalApproval.setNamaKaryawan(userDetailKaryawan.getNama());
        finalApproval.setNipAtasan(userDetailAtasan.getUserUsername());
        finalApproval.setNamaAtasan(userDetailAtasan.getNama());

        finalApproval.setRemarks("All approvals completed successfully.");


        List<UserResponseDTO> listUserDTO = adminService.findUsersWithRolesNotContainingV2("USER");

        asyncEmailService.sendNotificationsAndEmails(finalApproval.getUserDetailResign(), finalApproval.getUserDetailAtasan(), finalApproval.getNipKaryawanResign(), "Your Resignation Has been Approved by HR Admin and All Departements", "HR Admin and all departments have approved the resignation");


        return finalApprovalRepository.save(finalApproval);
    }





//    private void sendNotificationsAndEmails(UserDetail userDetail, UserDetail userDetailAtasan, String nipKaryawanResign) {
//        String emailAtasan = userDetailAtasan.getEmail();
//        String userEmail = userDetail.getEmail();
//        String userNama = userDetail.getNama();
//        String atasanNama = userDetailAtasan.getNama();
//
//        notificationService.sendNotification("Approval Required: Resignation Request from: " + nipKaryawanResign + ", " + userNama, userDetail, userDetailAtasan.getUserUsername());
//        notificationService.sendNotification("Resignation request submitted", userDetail, nipKaryawanResign);
//
//        String linkKaryawan = "http://localhost:4200/#/progress-approval";
//        String linkAtasan = "http://localhost:4200/#/approval-atasan";
//
//        Map<String, Object> variablesKaryawan = emailTemplateService.createEmailVariables(userNama, "Your resignation request has been submitted.", linkKaryawan);
//        Map<String, Object> variablesAtasan = emailTemplateService.createEmailVariables(atasanNama, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, linkAtasan);
//
//        try {
//            emailTemplateService.sendHtmlEmail(userEmail, "Resignation Request Submitted", "email-template", variablesKaryawan);
//            emailTemplateService.sendHtmlEmail(emailAtasan, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, "email-template", variablesAtasan);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            // Handle exception
//        }
//    }


}
