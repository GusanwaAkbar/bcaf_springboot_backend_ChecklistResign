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



    public void sendEmail(UserDetail userDetailKaryawan, UserDetail userDetailAtasan, String emailSubject, String emailMessage) {
        // Validate and set user details
        if (userDetailAtasan == null) {
            throw new IllegalArgumentException("UserDetailAtasan must not be null");
        }

        String customerName = userDetailKaryawan.getNama();
        String phoneNumber = "081230648290"; // Assuming this is a fixed value or needs to be retrieved differently
        String email = userDetailKaryawan.getEmail();

        if (customerName == null || email == null) {
            throw new IllegalArgumentException("CustomerName and email must not be null");
        }

        // Validate emailSubject and emailMessage
        if (emailSubject == null || emailMessage == null) {
            throw new IllegalArgumentException("EmailSubject and emailMessage must not be null");
        }

        String emailBody = createEmailBody(customerName, phoneNumber, email, emailSubject, emailMessage);

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
                "phoneNumber", phoneNumber,
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




    private String createEmailBody(String customerName, String phoneNumber, String email, String emailSubject, String emailMessage) {
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
                "<p>Approval Required: New Resignation Request from " + phoneNumber + ", " + emailMessage + "</p>" +
                "<p><a href=\"http://link-to-application\">Klik di sini untuk membuka aplikasi</a></p>" +
                "<p>Salam penutup,</p>" +
                "<p>BCA Finance</p>" +
                "</body>" +
                "</html>";
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
}
