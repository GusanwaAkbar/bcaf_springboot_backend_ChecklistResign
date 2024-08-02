package com.hrs.checklist_resign.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.checklist_resign.Model.UserDetail;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceV2 {



    public void sendEmail(UserDetail userDetailKaryawan, UserDetail userDetailAtasan, String emailSubject, String emailMessage,String tujuan) {
        // Validate and set user details
        if (userDetailAtasan == null) {
            throw new IllegalArgumentException("UserDetailAtasan must not be null");
        }

        String customerName = "";
        String nipKaryawan = userDetailKaryawan.getUserUsername(); // Assuming this is a fixed value or needs to be retrieved differently
        String namaKaryawan = userDetailKaryawan.getNama();
        String email = "";

        String emailBody = "";

        if (tujuan == "KARYAWAN")
        {
            email = userDetailKaryawan.getEmail();
            customerName = userDetailKaryawan.getNama();

            emailBody = createEmailBodyNotifKaryawan(customerName, nipKaryawan, emailSubject, emailMessage);
        }
        else if (tujuan == "ATASAN")
        {
            email = userDetailAtasan.getEmail();
            customerName = userDetailAtasan.getNama();

            emailBody = createEmailBodyApprovalAtasan(customerName, namaKaryawan, emailSubject, emailMessage);
        } else if (tujuan == "ATASAN_UPDATE") {

            email = userDetailAtasan.getEmail();
            customerName = userDetailAtasan.getNama();

            emailBody = createEmailBodyApprovalAtasanUpdate(customerName, namaKaryawan, emailSubject, emailMessage);
        }






        // Construct the JSON request body
        Map<String, Object> emailMap = Map.of(
                "EmailBody", emailBody,
                "EmailSubject", emailSubject,
                "EmailCc", "", // Replaced null with an empty string
                "EmailBcc", "", // Replaced null with an empty string
                "EmailIdDataSources", 2,
                "EmailAttachmentName", "" // Replaced null with an empty string
        );

        Map<String, Object> requestBody = Map.of(
                "customerName", customerName,
                "sources", "OFFERING",
                "email", email,
                "rule", "OFFERING",
                "notificationList", List.of(Map.of(
                        "notificationType", "EMAIL",
                        "email", emailMap
                ))
        );

        // Send the request
        sendPostRequest("https://notifengine-hotfixapi-sit.idofocus.co.id:25443/api/submit", requestBody);
    }


    private void sendPostRequest(String urlString, Map<String, Object> requestBody) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Convert the requestBody map to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(requestBody);

            // Send the JSON input string
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Handle the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Successfully sent email
                System.out.println("Email sent successfully.");
            } else {
                // Handle error response
                System.out.println("Failed to send email. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //================ GENERIC SEND EMAIL SESSION =================


    public void sendDepartmentEmail(UserDetail userDetailKaryawan, UserDetail userDetailAtasanResign, String nipKaryawan, String departmentName, boolean isAccept) {
        String subject;
        String body;

        if (isAccept) {
            subject = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has Approved Your Resignation";
            body = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has Approved Your Resignation, Check the Progress In the App";
            sendEmail(userDetailKaryawan, userDetailAtasanResign, subject, body, "KARYAWAN");

            subject = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has Approved The Resignation";
            body = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has Approved The Resignation, please check the progress in the app";
            sendEmail(userDetailKaryawan, userDetailAtasanResign, subject, body, "ATASAN_UPDATE");
        } else {
            subject = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has PENDING Your Resignation";
            body = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has PENDING Your Resignation, Please Contact the admin and Check the Progress In the App";
            sendEmail(userDetailKaryawan, userDetailAtasanResign, subject, body, "KARYAWAN");

            subject = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has PENDING the Resignation";
            body = "Checklist Resign " + nipKaryawan + ": " + departmentName + " Has PENDING the Resignation, Please Contact the admin and Check the Progress In the App";
            sendEmail(userDetailKaryawan, userDetailAtasanResign, subject, body, "ATASAN_UPDATE");
        }
    }




    //============== TEMPLATE SESSION ======================

    private String createEmailBodyApprovalAtasan(String customerName, String namaKaryawan , String emailSubject, String emailMessage) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>" + emailSubject + "</title>" +
                "</head>" +
                "<body>" +
                "<div class=\"header\">" +
                "<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/BCA_Finance.svg/798px-BCA_Finance.svg.png\" alt=\"BCA Finance Logo\" width=\"200\">" +
                "</div>" +
                "<p>Pemberitahuan Pesan Otomatis dari Sistem Pesan Aplikasi Checklist Resign BCA Finance</p>" +
                "<p>Dear Bapak/Ibu " + customerName + "</p>" +
                "<p> Checklist Resign: New Resignation Request from " + namaKaryawan +", " + emailMessage + "</p>" +
                "<p><a href=\"http://localhost:4200/#/approval-atasan\">Klik di sini untuk membuka aplikasi (Harap terkoneksi dengan LAN) </a></p>" +
                "<p>Salam penutup,</p>" +
                "<p>BCA Finance</p>" +
                "</body>" +
                "</html>";
    }

    private String createEmailBodyApprovalAtasanUpdate(String customerName, String namaKaryawan , String emailSubject, String emailMessage) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>" + emailSubject + "</title>" +
                "</head>" +
                "<body>" +
                "<div class=\"header\">" +
                "<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/BCA_Finance.svg/798px-BCA_Finance.svg.png\" alt=\"BCA Finance Logo\" width=\"200\">" +
                "</div>" +
                "<p>Pemberitahuan Pesan Otomatis dari Sistem Pesan Aplikasi Checklist Resign BCA Finance</p>" +
                "<p>Dear Bapak/Ibu " + customerName + "</p>" +
                "<p> Update Checklist Resign of: "+namaKaryawan +  "</p>" +
                emailMessage +
                "<p><a href=\"http://localhost:4200/#/admin-pengajuanresign-list\">Klik di sini untuk membuka aplikasi (Harap terkoneksi dengan LAN) </a></p>" +
                "<p>Salam penutup,</p>" +
                "<p>BCA Finance</p>" +
                "</body>" +
                "</html>";
    }


    private String createEmailBodyNotifKaryawan(String customerName, String email, String emailSubject, String emailMessage) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>" + emailSubject + "</title>" +
                "</head>" +
                "<body>" +
                "<div class=\"header\">" +
                "<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/BCA_Finance.svg/798px-BCA_Finance.svg.png\" alt=\"BCA Finance Logo\" width=\"200\">" +
                "</div>" +
                "<p>Pemberitahuan Pesan Otomatis dari Sistem Pesan Aplikasi Checklist Resign BCA Finance</p>" +
                "<p>Yth Bapak/Ibu " + customerName + "</p>" +
                "<p> "+emailMessage+" </p>" +
                "<p><a href=\"http://localhost:4200/#/progress-approval\">Klik di sini untuk membuka aplikasi (Harap terkoneksi dengan LAN)  </a></p>" +
                "<p>Salam penutup,</p>" +
                "<p>BCA Finance</p>" +
                "</body>" +
                "</html>";
    }



}
