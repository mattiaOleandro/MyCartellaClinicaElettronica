package it.angelo.MyCartellaClinicaElettronica.auth.controllers;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.*;
import it.angelo.MyCartellaClinicaElettronica.auth.services.SignupService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
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

    /**
     * @param signupDTO contains all user attributes that will be inserted in the body of the API
     * @return a signupDTO object
     * @throws Exception can throw a generic exception
     */
    @PostMapping("/signup")
    public User signup(@RequestBody SignupDTO signupDTO)throws Exception{
        return signupService.signup(signupDTO);
    }

    @PostMapping("/signup/doctor/{role}")
    public User signupDoctor(@RequestBody SignupDoctorDTO signupDoctorDTO, @PathVariable String role)throws Exception{
        return signupService.signupDoctor(signupDoctorDTO, role);
    }

    @PostMapping("/signup/patient/{role}")
    public User signupPatient(@RequestBody SignupPatientDTO signupPatientDTO, @PathVariable String role)throws Exception{
        return signupService.signupPatient(signupPatientDTO, role);
    }

    @PostMapping("/signup/secretary/{role}")
    public User signupSecretary(@RequestBody SignupSecretaryDTO signupSecretaryDTO, @PathVariable String role)throws Exception{
        return signupService.signupSecretary(signupSecretaryDTO, role);
    }

    @PostMapping("/signup/{role}")
    public User signup(@RequestBody SignupDTO signupDTO, @PathVariable String role)throws Exception{
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
        return signupService.activate(signupActivationDTO);
    }
}
