package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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



    private String nomerWA;

    @OneToMany(mappedBy = "userDetailAtasan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<FinalApproval> finalApprovals;


    @OneToOne(mappedBy = "userDetailResign", cascade = CascadeType.ALL)
    @JsonIgnore
    private PengajuanResign pengajuanResign;

    @OneToMany(mappedBy = "userDetailAtasan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ApprovalAtasan> approvalAtasan;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Notification> sentNotifications;

    @ManyToMany(mappedBy = "recipients")
    @JsonIgnore
    private Set<Notification> receivedNotifications;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getNomerWA() {
        return nomerWA;
    }

    public void setNomerWA(String nomerWA) {
        this.nomerWA = nomerWA;
    }

//    public String getEmailAktif() {
//        return emailAktif;
//    }
//
//    public void setEmailAktif(String emailAktif) {
//        this.emailAktif = emailAktif;
//    }
}
