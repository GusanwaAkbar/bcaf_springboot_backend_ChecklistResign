package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
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
    public void sendNotificationsAndEmails(UserDetail userDetail, UserDetail userDetailAtasan, String nipKaryawanResign, String messageForKaryawan, String messageForAdmin) {
        String emailAtasan = userDetailAtasan.getEmail();
        String userEmail = userDetail.getEmail();
        String userNama = userDetail.getNama();
        String atasanNama = userDetailAtasan.getNama();

        //notif for admin
        notificationService.sendNotification(messageForAdmin + nipKaryawanResign + "from: " + userNama, userDetail, userDetailAtasan.getUserUsername());

        //notif for karyawan
        notificationService.sendNotification(messageForKaryawan, userDetail, nipKaryawanResign);

        String linkKaryawan = "http://localhost:4200/#/progress-approval";
        String linkAtasan = "http://localhost:4200/#/approval-atasan";

        Map<String, Object> variablesKaryawan = emailTemplateService.createEmailVariables(userNama, messageForKaryawan, linkKaryawan);
        Map<String, Object> variablesAtasan = emailTemplateService.createEmailVariables(atasanNama, messageForAdmin+ "from: " + nipKaryawanResign + ", " + userNama, linkAtasan);

        try {
            //email for admin
            emailTemplateService.sendHtmlEmail(emailAtasan, messageForAdmin+ "from: " + nipKaryawanResign + ", " + userNama, "email-template", variablesAtasan);

            //email for karyawan
            emailTemplateService.sendHtmlEmail(userEmail, messageForKaryawan, "email-template", variablesKaryawan);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void sendNotificationAndEmailsV2(String namaDepartement,ApprovalAtasan approvalAtasan, boolean isAccept)
    {
        UserDetail userDetailKaryawan = approvalAtasan.getPengajuanResign().getUserDetailResign();
        String nipKaryawan = approvalAtasan.getNipKaryawanResign();
        String namaKaryawan = approvalAtasan.getNamaKaryawan();

        //Set User Detail Atasan
        UserDetail userDetailAtasanResign = approvalAtasan.getUserDetailAtasan();

        // Send the email
        if (isAccept)
        {
            sendNotificationsAndEmails(userDetailKaryawan, userDetailAtasanResign, nipKaryawan, "Your Resignation has been approved by "+ namaDepartement, "Resignation of " +nipKaryawan +" " + namaKaryawan  +" has been approved by " + namaDepartement);
        }
        else {
            sendNotificationsAndEmails(userDetailKaryawan, userDetailAtasanResign, nipKaryawan,"Your Resignation is Pending by "+namaDepartement+ ", please contact the admin." ,  "Resignation of " +nipKaryawan +" " + namaKaryawan  + " is Pending by "+namaDepartement+", please contact the admin.");
        }
    }


}

