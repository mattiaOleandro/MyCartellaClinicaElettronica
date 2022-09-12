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
        if (signupDTO == null) throw new NullPointerException("signupDTO is null");
        return signupService.signup(signupDTO);
    }

    @PostMapping("/signup/doctor/{role}")
    public User signupDoctor(@RequestBody SignupDoctorDTO signupDoctorDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signupDoctor\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupDoctorDTO.getEmail()));
        if (signupDoctorDTO == null) throw new NullPointerException("signupDoctorDTO is null");
        if (role == null) throw new NullPointerException("role is null");
        return signupService.signupDoctor(signupDoctorDTO, role);
    }

    @PostMapping("/signup/patient/{role}")
    public User signupPatient(@RequestBody SignupPatientDTO signupPatientDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signupPatient\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupPatientDTO.getEmail()));
        if (signupPatientDTO == null) throw new NullPointerException("signupPatientDTO is null");
        if (role == null) throw new NullPointerException("role is null");
        return signupService.signupPatient(signupPatientDTO, role);
    }

    @PostMapping("/signup/secretary/{role}")
    public User signupSecretary(@RequestBody SignupSecretaryDTO signupSecretaryDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signupSecretary\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupSecretaryDTO.getEmail()));
        if (signupSecretaryDTO == null) throw new NullPointerException("signupSecretaryDTO is null");
        if (role == null) throw new NullPointerException("role is null");
        return signupService.signupSecretary(signupSecretaryDTO, role);
    }

    @PostMapping("/signup/{role}")
    public User signup(@RequestBody SignupDTO signupDTO, @PathVariable String role)throws Exception{
        logger.debug(String.format("@PostMapped \'/signup\' method called at %s at line# %d by %s",
                SignupController.class , lineGetter, signupDTO.getEmail()));
        if (signupDTO == null) throw new NullPointerException("signupDTO is null");
        if (role == null) throw new NullPointerException("role is null");
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
        if (signupActivationDTO == null) throw new NullPointerException("signupActivationDTO is null");
        return signupService.activate(signupActivationDTO);
    }
}
