package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.dto.FinalApprovalDTO;
import com.hrs.checklist_resign.Model.FinalApproval;
import com.hrs.checklist_resign.repository.FinalApprovalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinalApprovalService {

    @Autowired
    private FinalApprovalRepository finalApprovalRepository;

//    public Optional<FinalApproval> getFinalApprovalById(Long id) {
//        return finalApprovalRepository.findById(id);
//    }

    public FinalApproval createFinalApproval(FinalApproval finalApproval) {
        return finalApprovalRepository.save(finalApproval);
    }

    public FinalApproval updateFinalApproval(Long id, FinalApproval finalApprovalDetails) {
        Optional<FinalApproval> optionalFinalApproval = finalApprovalRepository.findById(id);
        if (optionalFinalApproval.isPresent()) {
            FinalApproval finalApproval = optionalFinalApproval.get();
            finalApproval.setApprovalTreasury(finalApprovalDetails.getApprovalTreasury());
            finalApproval.setApprovalGeneralServices(finalApprovalDetails.getApprovalGeneralServices());
            finalApproval.setApprovalHRIR(finalApprovalDetails.getApprovalHRIR());
            finalApproval.setApprovalHRLearning(finalApprovalDetails.getApprovalHRLearning());
            finalApproval.setApprovalHRPayroll(finalApprovalDetails.getApprovalHRPayroll());
            finalApproval.setApprovalHRServicesAdmin(finalApprovalDetails.getApprovalHRServicesAdmin());
            finalApproval.setApprovalHRTalent(finalApprovalDetails.getApprovalHRTalent());
            finalApproval.setApprovalSecurityAdministrator(finalApprovalDetails.getApprovalSecurityAdministrator());
            finalApproval.setFinalApprovalStatus(finalApprovalDetails.getFinalApprovalStatus());
            finalApproval.setRemarks(finalApprovalDetails.getRemarks());

            return finalApprovalRepository.save(finalApproval);
        } else {
            return null;
        }
    }





        public Optional<FinalApprovalDTO> getFinalApprovalById(Long id) {
            return finalApprovalRepository.findFinalApprovalDTOById(id);
        }

        @Transactional
    public List<FinalApprovalDTO> getAllFinalApproval() {
        return finalApprovalRepository.findAllFinalApprovalDTOs();
    }


}
