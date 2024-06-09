package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_approval_hrlearning")
public class ApprovalHRLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonBackReference
    private ApprovalAtasan approvalAtasan;

    @Column(name = "pengecekan_biaya_training")
    private String pengecekanBiayaTraining;

    @Column(name = "approval_hr_learning_status")
    private String approvalHRLearningStatus;

    //Setter and Getter


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

    public String getPengecekanBiayaTraining() {
        return pengecekanBiayaTraining;
    }

    public void setPengecekanBiayaTraining(String pengecekanBiayaTraining) {
        this.pengecekanBiayaTraining = pengecekanBiayaTraining;
    }

    public String getApprovalHRLearningStatus() {
        return approvalHRLearningStatus;
    }

    public void setApprovalHRLearningStatus(String approvalHRLearningStatus) {
        this.approvalHRLearningStatus = approvalHRLearningStatus;
    }
}
