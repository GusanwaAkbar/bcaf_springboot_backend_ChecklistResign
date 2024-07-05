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
    private AdminService adminService;

    public boolean doCheck(Long id) {

        System.out.println("=========================== do checking ===========================");

        return isApprovalStatusAccepted(approvalTreasuryRepository.findById(id).map(ApprovalTreasury::getApprovalTreasuryStatus)) &&
                isApprovalStatusAccepted(approvalGeneralServicesRepository.findById(id).map(ApprovalGeneralServices::getApprovalGeneralServicesStatus)) &&
                isApprovalStatusAccepted(approvalHRIRRepository.findById(id).map(ApprovalHRIR::getApprovalHRIRStatus)) &&
                isApprovalStatusAccepted(approvalHRLearningRepository.findById(id).map(ApprovalHRLearning::getApprovalHRLearningStatus)) &&
                isApprovalStatusAccepted(approvalHRPayrollRepository.findById(id).map(ApprovalHRPayroll::getApprovalHRPayrollStatus)) &&
                isApprovalStatusAccepted(approvalHRServicesAdminRepository.findById(id).map(ApprovalHRServicesAdmin::getApprovalHRServicesAdminStatus)) &&
                isApprovalStatusAccepted(approvalHRTalentRepository.findById(id).map(ApprovalHRTalent::getApprovalHRTalentStatus)) &&
                isApprovalStatusAccepted(approvalSecurityAdministratorRepository.findById(id).map(ApprovalSecurityAdministrator::getApprovalSecurityAdministratorStatus));
    }

    private boolean isApprovalStatusAccepted(Optional<String> status) {
        return status.isPresent() && "accept".equals(status.get());
    }

    public FinalApproval createFinalApproval(Long id) {

        //List<UserResponseDTO> userAdmin = adminService.findUsersWithRolesNotContainingV2("USER");

        System.out.println("=========================== do creating ===========================");

        FinalApproval finalApproval = new FinalApproval();

        finalApproval.setUserDetailAtasan(approvalTreasuryRepository.findById(id).get().getApprovalAtasan().getUserDetailAtasan());

        finalApproval.setUserDetailResign(approvalTreasuryRepository.findById(id).get().getApprovalAtasan().getPengajuanResign().getUserDetailResign());

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
        finalApproval.setRemarks("All approvals completed successfully.");


        List<UserResponseDTO> listUserDTO = adminService.findUsersWithRolesNotContainingV2("USER");

        return finalApprovalRepository.save(finalApproval);
    }





    private void sendNotificationsAndEmails(UserDetail userDetail, UserDetail userDetailAtasan, String nipKaryawanResign) {
        String emailAtasan = userDetailAtasan.getEmail();
        String userEmail = userDetail.getEmail();
        String userNama = userDetail.getNama();
        String atasanNama = userDetailAtasan.getNama();

        notificationService.sendNotification("Approval Required: Resignation Request from: " + nipKaryawanResign + ", " + userNama, userDetail, userDetailAtasan.getUserUsername());
        notificationService.sendNotification("Resignation request submitted", userDetail, nipKaryawanResign);

        String linkKaryawan = "http://localhost:4200/#/progress-approval";
        String linkAtasan = "http://localhost:4200/#/approval-atasan";

        Map<String, Object> variablesKaryawan = emailTemplateService.createEmailVariables(userNama, "Your resignation request has been submitted.", linkKaryawan);
        Map<String, Object> variablesAtasan = emailTemplateService.createEmailVariables(atasanNama, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, linkAtasan);

        try {
            emailTemplateService.sendHtmlEmail(userEmail, "Resignation Request Submitted", "email-template", variablesKaryawan);
            emailTemplateService.sendHtmlEmail(emailAtasan, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, "email-template", variablesAtasan);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
