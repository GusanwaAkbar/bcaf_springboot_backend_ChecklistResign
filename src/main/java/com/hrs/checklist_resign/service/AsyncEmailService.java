package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.UserDetail;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AsyncEmailService {

    private final EmailTemplateService emailTemplateService;
    private final NotificationService notificationService;

    public AsyncEmailService(EmailTemplateService emailTemplateService, NotificationService notificationService) {
        this.emailTemplateService = emailTemplateService;
        this.notificationService = notificationService;
    }

    @Async
    public void sendNotificationsAndEmails(UserDetail userDetail, UserDetail userDetailAtasan, String nipKaryawanResign) {
        String emailAtasan = userDetailAtasan.getEmail();
        String userEmail = userDetail.getEmail();
        String userNama = userDetail.getNama();
        String atasanNama = userDetailAtasan.getNama();

        notificationService.sendNotification("Approval Required: Resignation Request from: " + nipKaryawanResign + ", " + userNama, userDetail, userDetailAtasan.getUserUsername());
        notificationService.sendNotification("Resignation request submitted", userDetail, nipKaryawanResign);

        String linkKaryawan = "http://localhost:4200/#/progress-approval";
        String linkAtasan = "http://localhost:4200/#/approval-atasan";

        Map<String, Object> variablesKaryawan = emailTemplateService.createEmailVariables(userNama, "Your resignation request has been submitted.", linkKaryawan);
        Map<String, Object> variablesAtasan = emailTemplateService.createEmailVariables(atasanNama, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, linkAtasan);

        try {
            emailTemplateService.sendHtmlEmail(userEmail, "Resignation Request Submitted", "email-template", variablesKaryawan);
            emailTemplateService.sendHtmlEmail(emailAtasan, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, "email-template", variablesAtasan);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}

