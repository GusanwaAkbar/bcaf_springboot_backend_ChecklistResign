package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_hrs_resign_user_details")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    @JsonBackReference
    private User user;

    private String nama;

    @JsonProperty("user_username")
    @Column(unique = true)
    public String getUserUsername() {
        return user != null ? user.getUsername() : null;
    }

    private String email;
    private String cabang;
    private String idDivisi;
    private String divisi;
    private String jabatan;
    private String externalUser;


    @OneToOne(mappedBy = "userDetailResign", cascade = CascadeType.ALL)
    @JsonIgnore
    private PengajuanResign pengajuanResign;

    @OneToOne(mappedBy = "userDetailAtasan", cascade = CascadeType.ALL)
    @JsonIgnore
    private ApprovalAtasan approvalAtasan;

    @Override
    public String toString() {
        return "UserDetail{" +
                "nip=" + id +
                ", nama='" + user.getUsername() + '\'' +
                ", email='" + email + '\'' +
                ", cabang='" + cabang + '\'' +
                ", idDivisi='" + idDivisi + '\'' +
                ", divisi='" + divisi + '\'' +
                ", jabatan='" + jabatan + '\'' +
                ", externalUser='" + externalUser + '\'' +
                '}';
    }

    // Getters and setters
    // ...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }



    public void setUser(User user) {
        this.user = user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PengajuanResign getPengajuanResign() {
        return pengajuanResign;
    }

    public void setPengajuanResign(PengajuanResign pengajuanResign) {
        this.pengajuanResign = pengajuanResign;
    }
}
