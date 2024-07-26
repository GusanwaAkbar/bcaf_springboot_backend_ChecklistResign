package com.hrs.checklist_resign.dto;

import com.hrs.checklist_resign.Model.UserDetail;

import java.util.Date;



public class ResignationProgressDTO {
    private Long id;
    private String nipUser;
    private String namaKaryawan;
    private Date tanggalBerakhirBekerja;
    private String nipAtasan;
    private String namaAtasan;
    private String emailAtasan;
    private Date createdDate;
    private Date approvedDate;
    private Date approvedDateAllDepartement;
    private Date approvedDateFinal;
    private UserDetail userDetailResign;

    public ResignationProgressDTO(Long id, String nipUser, String namaKaryawan, Date tanggalBerakhirBekerja, String nipAtasan, String namaAtasan, String emailAtasan, Date createdDate, Date approvedDate, Date approvedDateAllDepartement, Date approvedDateFinal, UserDetail userDetailResign) {

        this.id = id;
        this.nipUser = nipUser;
        this.namaKaryawan = namaKaryawan;
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
        this.nipAtasan = nipAtasan;
        this.namaAtasan = namaAtasan;
        this.emailAtasan = emailAtasan;
        this.createdDate = createdDate;
        this.approvedDate = approvedDate;
        this.approvedDateAllDepartement = approvedDateAllDepartement;
        this.approvedDateFinal = approvedDateFinal;
        this.userDetailResign = userDetailResign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNipUser() {
        return nipUser;
    }

    public void setNipUser(String nipUser) {
        this.nipUser = nipUser;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public Date getTanggalBerakhirBekerja() {
        return tanggalBerakhirBekerja;
    }

    public void setTanggalBerakhirBekerja(Date tanggalBerakhirBekerja) {
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }

    public String getNamaAtasan() {
        return namaAtasan;
    }

    public void setNamaAtasan(String namaAtasan) {
        this.namaAtasan = namaAtasan;
    }

    public String getEmailAtasan() {
        return emailAtasan;
    }

    public void setEmailAtasan(String emailAtasan) {
        this.emailAtasan = emailAtasan;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getApprovedDateAllDepartement() {
        return approvedDateAllDepartement;
    }

    public void setApprovedDateAllDepartement(Date approvedDateAllDepartement) {
        this.approvedDateAllDepartement = approvedDateAllDepartement;
    }

    public Date getApprovedDateFinal() {
        return approvedDateFinal;
    }

    public void setApprovedDateFinal(Date approvedDateFinal) {
        this.approvedDateFinal = approvedDateFinal;
    }

    public UserDetail getUserDetailResign() {
        return userDetailResign;
    }

    public void setUserDetailResign(UserDetail userDetailResign) {
        this.userDetailResign = userDetailResign;
    }
}