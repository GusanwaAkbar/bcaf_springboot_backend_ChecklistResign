//package com.hrs.checklist_resign.Model;
//
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "app_hrs_resign_bucket_pengajuan_resign")
//public class ProgressApproval {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "user_detail_id", referencedColumnName = "id")
//    private UserDetail userDetailResign;
//
//
//    private boolean statusApprovalAtasan;
//
//    private boolean statusApprovalDepartement;
//
//    private boolean statusApprovalAKhir;
//
//}
