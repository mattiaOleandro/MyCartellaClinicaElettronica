package it.angelo.MyCartellaClinicaElettronica.notification;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * manages the email notification service
 */
@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * send an email with the activation code to the user
     * @param user the user you want to activate
     */
    public void sendActivationEmail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail()); //setto l'email dell'utente a cui voglio inviare
        sms.setSubject("Ti sei iscritto su MyCartellaClinicaElettronica");
        sms.setFrom("develhope.test.agosto.2022@gmail.com");
        sms.setReplyTo("develhope.test.agosto.2022@gmail.com");
        sms.setText("Il codice di attivazione è: " + user.getActivationCode());
        //sms.setText("Clicca quì per attivare http://localhost:8080/auth/signup/activation" + user.getActivationCode());
        emailSender.send(sms);
    }

    /**
     * send an email with the activation code to the user
     * for reset password
     * @param user the user you want to activate
     */
    public void sendPasswordResetMail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setSubject("Servizio di recupero password");
        sms.setFrom("develhope.test.agosto.2022@gmail.com");
        sms.setReplyTo("develhope.test.agosto.2022@gmail.com");
        sms.setText("Il codice di reset password è: " + user.getPasswordResetCode());
        //sms.setText("Clicca quì per resettare http://localhost:8080/auth/signup/activation" + user.getActivationCode());
        emailSender.send(sms);
    }
}
