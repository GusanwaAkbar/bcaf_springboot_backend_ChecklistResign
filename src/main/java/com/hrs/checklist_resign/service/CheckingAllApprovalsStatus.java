package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean doCheck(Long id) {
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
        FinalApproval finalApproval = new FinalApproval();
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

        return finalApprovalRepository.save(finalApproval);
    }
}
