package com.hrs.checklist_resign.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_approval_treasury")
public class ApprovalTreasury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonManagedReference(value = "approvalTreasury")
    private ApprovalAtasan approvalAtasan;

    @Column(name = "penyelesaian_biaya_advance")
    private String biayaAdvance;

    @Column(name = "pemblokiran_kartu_fleet")
    private String blokirFleet;

    @Column(name = "approval_treasury_status")
    private String approvalTreasuryStatus;

    private String remarks;

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

    public String getBiayaAdvance() {
        return biayaAdvance;
    }

    public void setBiayaAdvance(String biayaAdvance) {
        this.biayaAdvance = biayaAdvance;
    }

    public String getBlokirFleet() {
        return blokirFleet;
    }

    public void setBlokirFleet(String blokirFleet) {
        this.blokirFleet = blokirFleet;
    }

    public String getApprovalTreasuryStatus() {
        return approvalTreasuryStatus;
    }

    public void setApprovalTreasuryStatus(String approvalTreasuryStatus) {
        this.approvalTreasuryStatus = approvalTreasuryStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
