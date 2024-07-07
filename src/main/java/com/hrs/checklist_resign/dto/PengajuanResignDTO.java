package com.hrs.checklist_resign.dto;

import java.time.LocalDate;
import java.util.Date;

public class PengajuanResignDTO {
    private Long id;
    private Boolean isiUntukOrangLain;
    private Date tanggalPembuatanAkunHRIS;
    private Date tanggalBerakhirBekerja;
    private UserDetailDTO userDetailDTO;
    private String nipAtasan;
    private String emailAtasan;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public PengajuanResignDTO() {}

    public PengajuanResignDTO(Long id, Boolean isiUntukOrangLain, Date tanggalPembuatanAkunHRIS,
                              Date tanggalBerakhirBekerja, UserDetailDTO userDetailDTO, String nipAtasan,
                              String emailAtasan, Date approvedDate, Date createdDate, String approvedBy,
                              String documentPath) {
        this.id = id;
        this.isiUntukOrangLain = isiUntukOrangLain;
        this.tanggalPembuatanAkunHRIS = tanggalPembuatanAkunHRIS;
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
        this.userDetailDTO = userDetailDTO;
        this.nipAtasan = nipAtasan;
        this.emailAtasan = emailAtasan;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.documentPath = documentPath;
    }


    // Getters and setters (if needed)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsiUntukOrangLain() {
        return isiUntukOrangLain;
    }

    public void setIsiUntukOrangLain(Boolean isiUntukOrangLain) {
        this.isiUntukOrangLain = isiUntukOrangLain;
    }

    public Date getTanggalPembuatanAkunHRIS() {
        return tanggalPembuatanAkunHRIS;
    }

    public void setTanggalPembuatanAkunHRIS(Date tanggalPembuatanAkunHRIS) {
        this.tanggalPembuatanAkunHRIS = tanggalPembuatanAkunHRIS;
    }

    public Date getTanggalBerakhirBekerja() {
        return tanggalBerakhirBekerja;
    }

    public void setTanggalBerakhirBekerja(Date tanggalBerakhirBekerja) {
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
    }

    public UserDetailDTO getUserDetailDTO() {
        return userDetailDTO;
    }

    public void setUserDetailDTO(UserDetailDTO userDetailDTO) {
        this.userDetailDTO = userDetailDTO;
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

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
