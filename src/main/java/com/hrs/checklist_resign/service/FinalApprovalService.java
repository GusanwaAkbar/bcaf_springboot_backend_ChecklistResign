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

    public FinalApproval updateFinalApproval(FinalApproval finalApproval) {
        return finalApprovalRepository.save(finalApproval);
    }


        public Optional<FinalApprovalDTO> getFinalApprovalById(Long id) {
            return finalApprovalRepository.findFinalApprovalDTOById(id);
        }

    public Optional<FinalApproval> getFinalApprovalByIdv2(Long id) {
        return finalApprovalRepository.findById(id);
    }


    @Transactional
    public List<FinalApprovalDTO> getAllFinalApproval() {
        return finalApprovalRepository.findAllFinalApprovalDTOs();
    }


}
