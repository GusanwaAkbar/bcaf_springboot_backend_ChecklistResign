package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "app_hrs_resign_approval_hrir")
public class ApprovalHRIR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonBackReference
    private ApprovalAtasan approvalAtasan;


    @Value("${exitInterview:false}")
    @Column(name = "exit_interview")
    private Boolean exitInterview;

    @Value("${approvalHRIR:false}")
    private boolean approvalHRIRStatus;




    //Getter Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApprovalAtasan getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasan approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public Boolean getExitInterview() {
        return exitInterview;
    }

    public void setExitInterview(Boolean exitInterview) {
        this.exitInterview = exitInterview;
    }


    public boolean isApprovalHRIRStatus() {
        return approvalHRIRStatus;
    }

    public void setApprovalHRIRStatus(boolean approvalHRIRStatus) {
        this.approvalHRIRStatus = approvalHRIRStatus;
    }
}
