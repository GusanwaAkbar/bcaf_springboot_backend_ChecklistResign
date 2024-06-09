package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.repository.ApprovalHRIRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalHRIRService {

    @Autowired
    ApprovalHRIRRepository approvalHRIRRepository;
    public ApprovalHRIR findApprovalHRIRById (Long id)
    {
        //get item by id using Repository
        ApprovalHRIR approvalHRIRObj  = approvalHRIRRepository.getReferenceById(id);

        return approvalHRIRObj;
    }

    public List<ApprovalHRIR> findAllApprovalHRIR()
    {
        //get all item
        List<ApprovalHRIR> allApprovalHRIR = approvalHRIRRepository.findAll();

        return  allApprovalHRIR;
    }

    public  ApprovalHRIR saveApprovalHRIR(ApprovalHRIR approvalHRIR)
    {
        return approvalHRIRRepository.save(approvalHRIR);
    }

    public void deleteApprovalHRIR(Long id)
    {
        approvalHRIRRepository.deleteById(id);
    }
}
