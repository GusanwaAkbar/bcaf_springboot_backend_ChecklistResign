package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_approval_hrpayroll")
public class ApprovalHRPayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonBackReference
    private ApprovalAtasan approvalAtasan;

    @Column(name = "soft_loan")
    private String softLoan;

    @Column(name = "emergency_loan")
    private String emergencyLoan;

    @Column(name = "smartphone_loan")
    private String smartphoneLoan;

    @Column(name = "motor_loan")
    private String motorLoan;

    @Column(name = "umk_loan")
    private String umkLoan;

    @Column(name = "laptop_loan")
    private String laptopLoan;

    @Column(name = "approval_hr_payroll_status")
    private String approvalHRPayrollStatus;


    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftLoan() {
        return softLoan;
    }

    public void setSoftLoan(String softLoan) {
        this.softLoan = softLoan;
    }

    public String getEmergencyLoan() {
        return emergencyLoan;
    }

    public void setEmergencyLoan(String emergencyLoan) {
        this.emergencyLoan = emergencyLoan;
    }

    public String getSmartphoneLoan() {
        return smartphoneLoan;
    }

    public void setSmartphoneLoan(String smartphoneLoan) {
        this.smartphoneLoan = smartphoneLoan;
    }

    public String getMotorLoan() {
        return motorLoan;
    }

    public void setMotorLoan(String motorLoan) {
        this.motorLoan = motorLoan;
    }

    public String getUmkLoan() {
        return umkLoan;
    }

    public void setUmkLoan(String umkLoan) {
        this.umkLoan = umkLoan;
    }

    public String getLaptopLoan() {
        return laptopLoan;
    }

    public void setLaptopLoan(String laptopLoan) {
        this.laptopLoan = laptopLoan;
    }

    public String getApprovalHRPayrollStatus() {
        return approvalHRPayrollStatus;
    }

    public void setApprovalHRPayrollStatus(String approvalHRPayrollStatus) {
        this.approvalHRPayrollStatus = approvalHRPayrollStatus;
    }
}
