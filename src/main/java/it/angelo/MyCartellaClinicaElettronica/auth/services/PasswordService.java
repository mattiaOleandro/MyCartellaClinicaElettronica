package it.angelo.MyCartellaClinicaElettronica.auth.services;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.RequestPasswordDTO;
import it.angelo.MyCartellaClinicaElettronica.auth.entities.RestorePasswordDTO;
import it.angelo.MyCartellaClinicaElettronica.notification.MailNotificationService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * contains the business logic for reset and restore password
 */
@Service
public class PasswordService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(PasswordService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    /**
     * assign a new password and send it by email to the user
     * @param requestPasswordDTO contain an email attribute necessary for reset password
     * @return a user who is present in the database
     * @throws Exception a generic exception can be thrown
     */
    public User request(RequestPasswordDTO requestPasswordDTO) throws Exception {
        logger.info("Public method 'request' method called at "+ PasswordService.class +" at line#" + lineGetter);
        // verifico se sul DB è presente un user con una certa email
        User userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
        // se user è uguale a null o non è attivo, lancio un'eccezione
        if(userFromDB == null || !userFromDB.isActive()) throw new Exception("Cannot find user");
        logger.error("if statement from public PasswordService 'request' inside :"+
                PasswordService.class +" at line#" +
                lineGetter + "- Error : userFromDB is null");
        //assegno un codice temporaneo
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
        //invio il codice appena generato via email
        mailNotificationService.sendPasswordResetMail(userFromDB);
        //salvo user
        return userRepository.save(userFromDB);
    }

    /**
     * assign a new PasswordResetCode and save user with new password into database
     * @param restorePasswordDTO contain newPassword and resetPasswordCode
     * @return a user who is present in the database
     * @throws Exception a generic exception can be thrown
     */
    public User restore(RestorePasswordDTO restorePasswordDTO) throws Exception{
        logger.info("Public method 'restore' method called at "+ PasswordService.class +" at line#" + lineGetter);
        //verifico se sul DB è presente un user con un determinato PasswordResetCode
        User userFromDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());
        // se non è presente lancio un'eccezione
        if(userFromDB == null) throw new Exception("Cannot find user");
        logger.error("if statement from public PasswordService 'request' inside :"+
                PasswordService.class +" at line#" +
                lineGetter + "- Error : userFromDB is null");

        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);

        //activate the user
        userFromDB.setActive(true);
        //clean ActivationCode field
        userFromDB.setActivationCode(null);

        return userRepository.save(userFromDB);
    }
}
