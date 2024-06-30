package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nip_user")
    private String nipUser;

    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserDetail sender;

    private LocalDateTime timestamp;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "notification_recipients",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_detail_id")
    )
    private Set<UserDetail> recipients;


    //getter and setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDetail getSender() {
        return sender;
    }

    public void setSender(UserDetail sender) {
        this.sender = sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Set<UserDetail> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<UserDetail> recipients) {
        this.recipients = recipients;
    }

    public String getNipUser() {
        return nipUser;
    }

    public void setNipUser(String nipUser) {
        this.nipUser = nipUser;
    }
}
