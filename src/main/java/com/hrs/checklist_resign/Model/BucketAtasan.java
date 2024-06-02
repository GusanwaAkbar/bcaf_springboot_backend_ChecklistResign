package com.hrs.checklist_resign.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_atasan")
public class BucketAtasan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serah_terima_tugas_tanggung_jawab")
    private boolean serahTerimaTugasTanggungJawab;

    @Column(name = "pengembalian_notebook_aset_pinjaman")
    private boolean pengembalianNotebookAsetPinjaman;

    @Column(name = "pengembalian_kunci_loker")
    private boolean pengembalianKunciLoker;

    @Column(name = "pengembalian_kunci_ruangan")
    private boolean pengembalianKunciRuangan;

    @Column(name = "penyerahan_surat_pengunduran_diri")
    private boolean penyerahanSuratPengunduranDiri;

    @Column(name = "pengembalian_id_card_karyawan")
    private boolean pengembalianIdCardKaryawan;

    @Column(name = "menghapus_aplikasi_mobile")
    private boolean menghapusAplikasiMobile;

    @Column(name = "meng_uninstall_software_di_notebook")
    private boolean mengUninstallSoftwareDiNotebook;

    @Column(name = "meng_uninstall_software_di_unit_kerja_baru")
    private boolean mengUninstallSoftwareDiUnitKerjaBaru;

    @Column(name = "exit_interview")
    private boolean exitInterview;

    // Constructors, getters, and setters
}

// Similarly, define other entity classes for other tables like app_hrs_resign_treasurypayment, app_hrs_resign_hrservice, etc.
