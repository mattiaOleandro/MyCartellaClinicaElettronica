package it.angelo.MyCartellaClinicaElettronica.auth.controllers;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.RequestPasswordDTO;
import it.angelo.MyCartellaClinicaElettronica.auth.entities.RestorePasswordDTO;
import it.angelo.MyCartellaClinicaElettronica.auth.services.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(PasswordRestoreController.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

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
        logger.debug(String.format("@PostMapped \'/passwordRequest\' method called at %s at line# %d by %s",
                PasswordRestoreController.class , lineGetter, requestPasswordDTO.getEmail()));
        try {
            passwordService.request(requestPasswordDTO);
            if (requestPasswordDTO == null) throw new Exception("Password is null");
        }catch (Exception e){
            logger.error(String.format("if statement in \'/passwordRequest\' method called at %s at line# %d by %s - Error : %s",
                    PasswordRestoreController.class, lineGetter, requestPasswordDTO.getEmail() , e.getMessage()));
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
        logger.debug(String.format("@PostMapped \'/passwordRestore\' method called at %s at line# %d.",
                PasswordRestoreController.class , lineGetter));
        if (restorePasswordDTO == null) throw new NullPointerException("restorePasswordDTO is null.");
        passwordService.restore(restorePasswordDTO);
    }
}

//@PostMapping("/request")
//public void passwordRequest(@RequestBody RequestPasswordDTO requestPasswordDTO) throws Exception {
//    logger.info("@PostMapped '/request' method called at "+ PasswordRestoreController.class +" at line#" + lineGetter);
//    try {
//        passwordService.request(requestPasswordDTO);
//        if (requestPasswordDTO == null) throw new Exception("Password is null");
//    }catch (Exception e){
//        logger.error(String.format("@PostMapped '/request' method called at %s at line#%d
//                     - Error : %s", PasswordRestoreController.class, lineGetter, e.getMessage());
//    }
//}
