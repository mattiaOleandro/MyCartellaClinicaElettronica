package it.angelo.MyCartellaClinicaElettronica.auth.controllers;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.RequestPasswordDTO;
import it.angelo.MyCartellaClinicaElettronica.auth.entities.RestorePasswordDTO;
import it.angelo.MyCartellaClinicaElettronica.auth.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * handle service for restore user password
 */
@RestController
@RequestMapping("/auth/password")
public class PasswordRestoreController {


    @Autowired
    private PasswordService passwordService;

    /**
     * request an email to the user for reset password
     * invoke request method from PasswordService Class
     * @param requestPasswordDTO that contain an email string
     * @throws Exception a generic exception can be thrown
     */
    @PostMapping("/request")
    public void passwordRequest(@RequestBody RequestPasswordDTO requestPasswordDTO) throws Exception {
        try {
            passwordService.request(requestPasswordDTO);
        }catch (Exception e){

        }
    }

    /**
     * accept 2 String: newPassword and resetPasswordCode, located in RestorePasswordDTO Class
     * invoke restore method from PasswordService Class
     * @param restorePasswordDTO contain a newPassword and resetPasswordCode
     * @throws Exception a generic exception can be thrown
     */
    @PostMapping("/restore")
    public void passwordRestore(@RequestBody RestorePasswordDTO restorePasswordDTO) throws Exception{
        passwordService.restore(restorePasswordDTO);
    }
}
