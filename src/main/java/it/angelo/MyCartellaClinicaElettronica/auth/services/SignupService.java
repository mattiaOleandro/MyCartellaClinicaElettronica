package it.angelo.MyCartellaClinicaElettronica.auth.services;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.*;
import it.angelo.MyCartellaClinicaElettronica.notification.MailNotificationService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.Role;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.RoleRepository;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * it's a service tha contain business logic for handling user signup and activation
 */
@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(SignupService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    public User signup(SignupDTO signupDTO) throws Exception {
        logger.debug(String.format("\'/signup\' method called at %s at line# %d by %s",
                SignupService.class , lineGetter, signupDTO.getEmail()));
        return this.signup(signupDTO, Roles.REGISTERED);
    }

    /**
     * contains the business logic related to the signup
     * @param signupDTO represents the user data that will be transferred from the frontend
     * @param role the role of user
     * @return a user
     * @throws Exception can throw a generic exception
     */
    public User signup(SignupDTO signupDTO, String role) throws Exception { //SignupDTO rappresenta in Java l'oggetto body su Postman
        logger.debug(String.format("\'/signup\' method called at %s at line# %d by %s",
                SignupService.class , lineGetter, signupDTO.getEmail()));

        User userInDB = userRepository.findByEmail(signupDTO.getEmail());
        if(userInDB != null) throw new Exception("User already exist");
        // creo nuovo user e setto i parametri necessari
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setSurname(signupDTO.getSurname());
        //la password viene codificata con PasswordEncoder e assegnata all'utente
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setAddress(signupDTO.getAddress());
        user.setCity(signupDTO.getCity());
        user.setPhone(signupDTO.getPhone());
        user.setNationality(signupDTO.getNationality());
        user.setPlaceOfBirth(signupDTO.getPlaceOfBirth());
        user.setBirthDate(signupDTO.getBirthDate());
        user.setFiscalCode(signupDTO.getFiscalCode());
        user.setDocumentNumber(signupDTO.getDocumentNumber());
        //user.setActive(false); // aggiunto parametro in User class

        //genera un codice univoco di 36 caratteri
        user.setActivationCode(UUID.randomUUID().toString());

        //assegniamo all'utente un set di ruoli
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set user role");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);// invio mail di attivazione
        return userRepository.save(user); // ritorniamo l'user salvato
    }

    public User signupDoctor(SignupDoctorDTO signupDoctorDTO, String role) throws Exception{
        logger.debug(String.format("\'/signupDoctor\' method called at %s at line# %d by %s",
                SignupService.class , lineGetter, signupDoctorDTO.getEmail()));

        User userInDB = userRepository.findByEmail(signupDoctorDTO.getEmail());
        if(userInDB != null) throw new Exception("Doctor already exist");

        User user = new User();
        user.setName(signupDoctorDTO.getName());
        user.setEmail(signupDoctorDTO.getEmail());
        user.setSurname(signupDoctorDTO.getSurname());
        //la password viene codificata con PasswordEncoder e assegnata all'utente
        user.setPassword(passwordEncoder.encode(signupDoctorDTO.getPassword()));
        user.setAddress(signupDoctorDTO.getAddress());
        user.setCity(signupDoctorDTO.getCity());
        user.setPhone(signupDoctorDTO.getPhone());
        user.setNationality(signupDoctorDTO.getNationality());
        user.setPlaceOfBirth(signupDoctorDTO.getPlaceOfBirth());
        user.setBirthDate(signupDoctorDTO.getBirthDate());
        user.setFiscalCode(signupDoctorDTO.getFiscalCode());
        user.setDocumentNumber(signupDoctorDTO.getDocumentNumber());

        user.setBadgeNumber(signupDoctorDTO.getBadgeNumber());
        user.setMedicalSpecialization(signupDoctorDTO.getMedicalSpecialization());
        user.setPlaceOfWork(signupDoctorDTO.getPlaceOfWork());
        user.setPassword(passwordEncoder.encode(signupDoctorDTO.getPassword()));

        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole =roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set Doctor role");

        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);
    }

    public User signupPatient(SignupPatientDTO signupPatientDTO, String role) throws Exception{
        logger.debug(String.format("\'/signupPatient\' method called at %s at line# %d by %s",
                SignupService.class , lineGetter, signupPatientDTO.getEmail()));

        User userInDB = userRepository.findByEmail(signupPatientDTO.getEmail());
        if(userInDB != null) throw new Exception("Patient already exist");

        User user = new User();
        user.setName(signupPatientDTO.getName());
        user.setEmail(signupPatientDTO.getEmail());
        user.setSurname(signupPatientDTO.getSurname());
        //la password viene codificata con PasswordEncoder e assegnata all'utente
        user.setPassword(passwordEncoder.encode(signupPatientDTO.getPassword()));
        user.setAddress(signupPatientDTO.getAddress());
        user.setCity(signupPatientDTO.getCity());
        user.setPhone(signupPatientDTO.getPhone());
        user.setNationality(signupPatientDTO.getNationality());
        user.setPlaceOfBirth(signupPatientDTO.getPlaceOfBirth());
        user.setBirthDate(signupPatientDTO.getBirthDate());
        user.setFiscalCode(signupPatientDTO.getFiscalCode());
        user.setDocumentNumber(signupPatientDTO.getDocumentNumber());

        user.setMedicalPathology(signupPatientDTO.getMedicalPathology());

        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole =roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set Patient role");


        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);
    }

    public User signupSecretary(SignupSecretaryDTO signupSecretaryDTO, String role) throws Exception{
        logger.debug(String.format("\'/signupSecretary\' method called at %s at line# %d by %s",
                SignupService.class , lineGetter, signupSecretaryDTO.getEmail()));
        User userInDB = userRepository.findByEmail(signupSecretaryDTO.getEmail());
        if(userInDB != null) throw new Exception("Secretary already exist");

        User user = new User();
        user.setName(signupSecretaryDTO.getName());
        user.setEmail(signupSecretaryDTO.getEmail());
        user.setSurname(signupSecretaryDTO.getSurname());
        //la password viene codificata con PasswordEncoder e assegnata all'utente
        user.setPassword(passwordEncoder.encode(signupSecretaryDTO.getPassword()));
        user.setAddress(signupSecretaryDTO.getAddress());
        user.setCity(signupSecretaryDTO.getCity());
        user.setPhone(signupSecretaryDTO.getPhone());
        user.setNationality(signupSecretaryDTO.getNationality());
        user.setPlaceOfBirth(signupSecretaryDTO.getPlaceOfBirth());
        user.setBirthDate(signupSecretaryDTO.getBirthDate());
        user.setFiscalCode(signupSecretaryDTO.getFiscalCode());
        user.setDocumentNumber(signupSecretaryDTO.getDocumentNumber());

        user.setPlaceOfWork(signupSecretaryDTO.getPlaceOfWork());

        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole =roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set Secretary role");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);
    }

    //possiamo dare una scadenza a questo activation code activationCodeExpirationDate
    //potrebbe essere utile ogni tot tempo, creare una routine per eliminare gli utenti che si sono
    //registrati MA mai attivati.

    /**
     * this method activate user after signup
     * @param signupActivationDTO contain the activationCode
     * @return a user
     * @throws Exception can throw a generic exception
     */
    public User activate(SignupActivationDTO signupActivationDTO) throws Exception {
        logger.debug(String.format("\'/activate\' method called at %s at line# %d .",
                SignupService.class , lineGetter));
        User user = userRepository.findByActivationCode(signupActivationDTO.getActivationCode());
        if(user == null) throw  new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
