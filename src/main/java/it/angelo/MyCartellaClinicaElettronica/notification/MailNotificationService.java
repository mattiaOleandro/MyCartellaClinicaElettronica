package it.angelo.MyCartellaClinicaElettronica.notification;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender emailSender;
    public void sendActivationEmail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setSubject("Ti sei iscritto alla piattaforma");
        sms.setFrom("development@develhope.co");
        sms.setReplyTo("development@develhope.co");
        sms.setText("Il codice di attivazione è: " + user.getActivationCode());
        emailSender.send(sms);
    }

    public void sendPasswordResetMail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setSubject("Ti sei iscritto alla piattaforma");
        sms.setFrom("development@develhope.co");
        sms.setReplyTo("development@develhope.co");
        sms.setText("Il codice di attivazione è: " + user.getPasswordResetCode());
        emailSender.send(sms);
    }
}
