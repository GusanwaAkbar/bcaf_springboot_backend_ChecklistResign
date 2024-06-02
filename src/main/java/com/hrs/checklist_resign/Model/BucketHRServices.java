package com.hrs.checklist_resign.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_hrservice")
public class BucketHRServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "penyelesaian_advance_biaya_di_hr")
    private String penyelesaianAdvanceBiayaDiHr;

    @Column(name = "file_advance_biaya")
    private String fileAdvanceBiaya;

    @Column(name = "excess_of_claim_hutang_kelebihan_biaya_berobat")
    private String excessOfClaimHutangKelebihanBiayaBerobat;

    @Column(name = "file_kelebihan_biaya_berobat")
    private String fileKelebihanBiayaBerobat;

    @Column(name = "penonaktifan_kartu_elektronik_asuransi_kesehatan")
    private String penonaktifanKartuElektronikAsuransiKesehatan;

    // Constructors, getters, and setters
}
