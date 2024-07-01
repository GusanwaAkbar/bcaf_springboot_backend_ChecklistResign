package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.Notification;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;



    @Autowired
    private UserDetailsService userDetailsService;

    public Notification sendNotification(String message, UserDetail sender, String recipient) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSender(sender);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRecipient(recipient);

        return notificationRepository.save(notification);
    }

    public List<Notification> findByRecepient(String nipUser)
    {


        return notificationRepository.findByRecipient(nipUser);
    }

//    public List<Notification> findByNipUser(String nipUser) {
//        return notificationRepository.findByNipUser(nipUser);
//    }


}
