package com.hrs.checklist_resign.dto;

import java.time.LocalDate;

public class PengajuanResignDTO {
    private Long id;
    private String isiUntukOrangLain;
    private LocalDate tanggalPembuatanAkunHRIS;
    private LocalDate tanggalBerakhirBekerja;
    private UserDetailDTO userDetailResign;
    private String nipAtasan;
    private String emailAtasan;

    //Constructor
    // No-arg constructor
    public PengajuanResignDTO() {}

    // All-arg constructor
    public PengajuanResignDTO(Long id, String isiUntukOrangLain, String tanggalPembuatanAkunHRIS, String tanggalBerakhirBekerja, UserDetailDTO userDetailResign, String nipAtasan, String emailAtasan) {
        this.id = id;
        this.isiUntukOrangLain = isiUntukOrangLain;
        this.tanggalPembuatanAkunHRIS = LocalDate.parse(tanggalPembuatanAkunHRIS);
        this.tanggalBerakhirBekerja = LocalDate.parse(tanggalBerakhirBekerja);
        this.userDetailResign = userDetailResign;
        this.nipAtasan = nipAtasan;
        this.emailAtasan = emailAtasan;
    }




    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsiUntukOrangLain() {
        return isiUntukOrangLain;
    }

    public void setIsiUntukOrangLain(String isiUntukOrangLain) {
        this.isiUntukOrangLain = isiUntukOrangLain;
    }

    public LocalDate getTanggalPembuatanAkunHRIS() {
        return tanggalPembuatanAkunHRIS;
    }

    public void setTanggalPembuatanAkunHRIS(LocalDate tanggalPembuatanAkunHRIS) {
        this.tanggalPembuatanAkunHRIS = tanggalPembuatanAkunHRIS;
    }

    public LocalDate getTanggalBerakhirBekerja() {
        return tanggalBerakhirBekerja;
    }

    public void setTanggalBerakhirBekerja(LocalDate tanggalBerakhirBekerja) {
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
    }

    public UserDetailDTO getUserDetailResign() {
        return userDetailResign;
    }

    public void setUserDetailResign(UserDetailDTO userDetailResign) {
        this.userDetailResign = userDetailResign;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }

    public String getEmailAtasan() {
        return emailAtasan;
    }

    public void setEmailAtasan(String emailAtasan) {
        this.emailAtasan = emailAtasan;
    }
}
