package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.repository.ApprovalHRTalentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.ListenerNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRTalentService {

    @Autowired
    ApprovalHRTalentRepository approvalHRTalentRepository;

    public ApprovalHRTalent saveApprovalHRTalent (ApprovalHRTalent approvalHRTalent)
    {
        return approvalHRTalentRepository.save(approvalHRTalent);
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

}
