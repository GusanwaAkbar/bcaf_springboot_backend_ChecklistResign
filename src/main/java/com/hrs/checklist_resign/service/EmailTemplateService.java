package com.hrs.checklist_resign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailTemplateService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendHtmlEmail(String to, String subject, String template, Map<String, Object> variables) throws MessagingException {
        if (to == null || to.isEmpty()) {
            // Log the error or handle it as needed, but don't throw an exception
            System.err.println("Email address is missing or invalid: " + to);
            return;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(template, context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.addInline("logo", new ClassPathResource("static/images/BCA_Finance.svg"));  // Ensure you have a logo.png in src/main/resources/static/images

        mailSender.send(message);
    }

    public Map<String, Object> createEmailVariables(String nama, String message, String link) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("nama", nama);
        variables.put("message", message);
        variables.put("link", link);

        return variables;
    }
}
