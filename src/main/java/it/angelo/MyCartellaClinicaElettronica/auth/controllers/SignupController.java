package it.angelo.MyCartellaClinicaElettronica.auth.controllers;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.*;
import it.angelo.MyCartellaClinicaElettronica.auth.services.SignupService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * creates the endpoints needed to manage the registration process
 */
@RestController
@RequestMapping("/auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    Logger logger = LoggerFactory.getLogger(SignupController.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();


    /**
     * @param signupDTO contains all user attributes that will be inserted in the body of the API
     * @return a signupDTO object
     * @throws Exception can throw a generic exception
     */

    @PostMapping("/signup")
    public User signup(@RequestBody SignupDTO signupDTO)throws Exception{
        logger.debug(String.format("@PostMapped \'/signup\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupDTO.getEmail()));
        if (signupDTO == null) throw new Exception("signupDTO is null");
            logger.error(String.format("if statement in \'/signup\' method called at %s at line# %d by %s - Error : signupDTO is null.",
                    SignupController.class, lineGetter, signupDTO.getEmail()));
        return signupService.signup(signupDTO);
    }

    @PostMapping("/signup/doctor/{role}")
    public User signupDoctor(@RequestBody SignupDoctorDTO signupDoctorDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signupDoctor\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupDoctorDTO.getEmail()));
        if (signupDoctorDTO == null)
            logger.error
                    (String.format("if statement in \'/signup\' method called at %s at line# %d by %s - Error : signupDoctorDTO is null.",
                    SignupController.class, lineGetter, signupDoctorDTO.getEmail()));
        if (role == null) logger.error
                (String.format("if statement in \'/signup\' method called at %s at line# %d by %s - Error : role is null.",
                        SignupController.class, lineGetter, signupDoctorDTO.getEmail()));
        return signupService.signupDoctor(signupDoctorDTO, role);
    }

    @PostMapping("/signup/patient/{role}")
    public User signupPatient(@RequestBody SignupPatientDTO signupPatientDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signupPatient\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupPatientDTO.getEmail()));
        if (signupPatientDTO == null)
            logger.error
                    (String.format("if statement in \'/signupPatient\' method called at %s at line# %d by %s - Error : signupPatientDTO is null.",
                            SignupController.class, lineGetter, signupPatientDTO.getEmail()));
        if (role == null) logger.error
                (String.format("if statement in \'/signupPatient\' method called at %s at line# %d by %s - Error : role is null.",
                        SignupController.class, lineGetter, signupPatientDTO.getEmail()));
        return signupService.signupPatient(signupPatientDTO, role);
    }

    @PostMapping("/signup/secretary/{role}")
    public User signupSecretary(@RequestBody SignupSecretaryDTO signupSecretaryDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signupSecretary\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupSecretaryDTO.getEmail()));
        if (signupSecretaryDTO == null)
            logger.error
                    (String.format("if statement in \'/signupSecretary\' method called at %s at line# %d by %s - Error : signupSecretaryDTO is null.",
                            SignupController.class, lineGetter, signupSecretaryDTO.getEmail()));
        if (role == null) logger.error
                (String.format("if statement in \'/signupSecretary\' method called at %s at line# %d by %s - Error : role is null.",
                        SignupController.class, lineGetter, signupSecretaryDTO.getEmail()));
        return signupService.signupSecretary(signupSecretaryDTO, role);
    }

    @PostMapping("/signup/{role}")
    public User signup(@RequestBody SignupDTO signupDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signup\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupDTO.getEmail()));
        if (signupDTO == null)
            logger.error(String.format("if statement in \'/signup\' method called at %s at line# %d by %s - Error : signupDTO is null.",
                    SignupController.class, lineGetter, signupDTO.getEmail()));
        if (role == null) logger.error
                (String.format("if statement in \'/signup\' method called at %s at line# %d by %s - Error : role is null.",
                        SignupController.class, lineGetter, signupDTO.getEmail()));
        return signupService.signup(signupDTO, role);
    }

    /**
     * this method activate user after signup
     * @param signupActivationDTO contain the activationCode
     * @return a user
     * @throws Exception can throw a generic exception
     */
    @PostMapping("/signup/activation")
    public User signup(@RequestBody SignupActivationDTO signupActivationDTO) throws Exception {
        logger.debug(String.format("@PostMapped \'/signup\' method called at %s at line# %d .",
                SignupController.class , lineGetter));
        if (signupActivationDTO == null)
            logger.error
                    (String.format("if statement in \'/signup\' method called at %s at line# %d - Error : signupActivationDTO is null.",
                            SignupController.class, lineGetter));
        return signupService.activate(signupActivationDTO);
    }
}
