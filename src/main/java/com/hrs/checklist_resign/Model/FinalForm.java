package com.hrs.checklist_resign.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_final_form")
public class FinalForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_detail_resign_id", referencedColumnName = "id")
    private UserDetail userDetailResign;

    @OneToOne
    @JoinColumn(name = "user_detail_atasan_id", referencedColumnName = "id")
    private  UserDetail userDetailAtasan;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    private  ApprovalAtasan approvalAtasan;

    @OneToOne
    @JoinColumn(name = "approval_hrtalent_id", referencedColumnName = "id")
    private ApprovalHRTalent approvalHRTalent;


}


