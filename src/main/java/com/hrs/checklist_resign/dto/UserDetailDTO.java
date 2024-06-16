package com.hrs.checklist_resign.dto;

public class UserDetailDTO {
    private Long id;
    private String nama;
    private String email;
    private String cabang;
    private String idDivisi;
    private String divisi;
    private String jabatan;
    private String externalUser;
    private String userUsername;

    //constructor

    public UserDetailDTO() {}

    // All-arg constructor
    public UserDetailDTO(Long id, String nama, String email, String cabang, String idDivisi, String divisi, String jabatan, String externalUser, String userUsername) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.cabang = cabang;
        this.idDivisi = idDivisi;
        this.divisi = divisi;
        this.jabatan = jabatan;
        this.externalUser = externalUser;
        this.userUsername = userUsername;
    }


    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getIdDivisi() {
        return idDivisi;
    }

    public void setIdDivisi(String idDivisi) {
        this.idDivisi = idDivisi;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getExternalUser() {
        return externalUser;
    }

    public void setExternalUser(String externalUser) {
        this.externalUser = externalUser;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}